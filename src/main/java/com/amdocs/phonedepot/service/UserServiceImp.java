/**
 *
 */
package com.amdocs.phonedepot.service;

import com.amdocs.phonedepot.dto.UserDTO;
import com.amdocs.phonedepot.dto.update.UserClientDTO;
import com.amdocs.phonedepot.dto.update.UserEmployeeDTO;
import com.amdocs.phonedepot.dto.validation.UserEmailDTO;
import com.amdocs.phonedepot.dto.validation.UsernameDTO;
import com.amdocs.phonedepot.enumeration.AppUserRole;
import com.amdocs.phonedepot.enumeration.Status;
import com.amdocs.phonedepot.model.User;
import com.amdocs.phonedepot.repository.IUserRepository;
import com.amdocs.phonedepot.security.OperationUtil;
import com.amdocs.phonedepot.service.interfaces.IUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Dhanapal
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements IUserService, UserDetailsService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final CloudinaryService cloudinaryService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Value("${sendgrid.verification.link}")
    private String verificationLink;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User with username: " + username + " not found");
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        } else {
            log.info("User found " + username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getUserRoles().getAuthority()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    authorities);
        }
    }

    @Override
    public User create(User user, MultipartFile file) {
        log.info("Saving new user: " + user.getName());
        log.info("Saving new user: " + user.getEmail());
        user.setIdUser(user.getIdUser());
        if (file != null) {
            try {
                user.setUrlImage(cloudinaryService.upload(file, "user"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Set automatically the user type Client
        user.setUserRoles(AppUserRole.ROLE_CLIENT);
        user.setDiscountPoint(0);
        user.setStatus(Status.ACTIVE);

        try {
            long otp = this.generateRandom();
            String verificationUrl = verificationLink + "?email=" + user.getEmail();
            mailService.sendVerificationEmail("dhanapal.jayapandi@amdocs.com", user.getEmail(), user.getName(), verificationUrl, otp);

            user.setExpiryTime(LocalDateTime.now().plusMinutes(3));
            user.setOtp(otp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userRepository.save(user);
    }

    @Override
    public User updateOTP(User user, Long otp) {
        user.setExpiryTime(LocalDateTime.now().plusMinutes(3));
        user.setOtp(otp);
        return userRepository.save(user);
    }

    @Override
    public boolean verifyEmail(User user, Long otp) {
        LocalDateTime expiryTime = user.getExpiryTime();

        log.info(LocalDateTime.now().toString());
        log.info(expiryTime.toString());
        if(LocalDateTime.now().isBefore(expiryTime) && user.getOtp() != null && user.getOtp().equals(otp)) {
            user.setOtp(null);
            user.setStatus(Status.ACTIVE);
            return true;
        }

        return false;
    }

    @Override
    public UserDTO update(Long id, User user, MultipartFile file) {
        log.info("Updating user with id: " + id);
        User userOld = userRepository.findByIdUser(id);
        userOld.setName(user.getName());
        userOld.setUsername(user.getUsername());
        userOld.setPhone(user.getPhone());
        userOld.setEmail(user.getEmail());
        if (user.getPassword() != null) {
            userOld.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userOld.setDiscountPoint(user.getDiscountPoint());
        userOld.setUrlImage(user.getUrlImage());
        if (file != null) {
            try {
                userOld.setUrlImage(cloudinaryService.upload(file, "user"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userOld.setStatus(user.getStatus());
        return convertUserToDTO(userRepository.save(userOld));
    }

    public UserDTO updateEmployee(UserEmployeeDTO userEmployeeDTO, long id) {
        log.info("Updating user with id: " + id);
        User userOld = userRepository.findByIdUser(id);
        userOld.setPhone(userEmployeeDTO.getPhone());
        userOld.setEmail(userEmployeeDTO.getEmail());
        return convertUserToDTO(userRepository.save(userOld));

    }

    public UserDTO updateClient(UserClientDTO userClientDTO, long id, MultipartFile file) {
        log.info("Updating user with id: " + id);
        User userOld = userRepository.findByIdUser(id);
        userOld.setName(userClientDTO.getName());
        userOld.setPhone(userClientDTO.getPhone());
        userOld.setEmail(userClientDTO.getEmail());
        userOld.setPassword(bCryptPasswordEncoder.encode(userClientDTO.getPassword()));
        userOld.setStatus(userClientDTO.getStatus());
        userOld.setUrlImage(userClientDTO.getUrlImage());
        if (file != null) {
            try {
                userOld.setUrlImage(cloudinaryService.upload(file, "user"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return convertUserToDTO(userRepository.save(userOld));
    }

    public String validationToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(OperationUtil.keyValue().getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodeJWT = verifier.verify(token);
            return decodeJWT.getSubject();
        } catch (Exception e) {
            log.error("Error logging in AuthorizationFilter: " + e.getMessage());
        }
        return null;
    }

    public User updatePasswordClient(String username, String password) {
        log.info("Updating password username: " + username);
        User user = userRepository.findByUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(Long iduser) {
        log.info("Deleting the user with id: " + iduser);
        if (userRepository.existsById(iduser)) {
            userRepository.deleteById(iduser);
            return true;
        } else {
            return false;
        }
    }

    public Collection<UserDTO> list(Long page) {
        log.info("List all users");
        return userRepository.list(page * 10).stream().map(this::convertUserToDTO).collect(Collectors.toList());
    }

    public UserDTO getUser(String username) {
        return convertUserToDTO(userRepository.findByUsername(username));
    }

    private UserDTO convertUserToDTO(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setIdUser(user.getIdUser());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setUrlImage(user.getUrlImage());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());
        userDto.setDiscountPoint(user.getDiscountPoint());
        userDto.setStatus(user.getStatus());
        return userDto;
    }

    @Override
    public Boolean exist(Long idUser) {
        log.info("Searching user by id: " + idUser);
        return  userRepository.existsById(idUser);
    }

	public Boolean exist(String username) {
		log.info("Searching user by username: " + username);
		User user = userRepository.findByUsername(username);
		return user != null;
	}

    public Collection<UserDTO> findByName(String name) {
        log.info("Searching user by name: " + name);

        return userRepository.findByNameStartsWith(name).stream().map(this::convertUserToDTO)
                .collect(Collectors.toList());

    }

    public UserDTO findByEmail(String email) {
        log.info("Searching user by email: " + email);
        return userRepository.findByEmail(email) != null ? convertUserToDTO(userRepository.findByEmail(email)) : null;

    }

    public UserEmailDTO findByEmailValid(String email) {
        log.info("Searching if the email " + email + " is valid");
        return userRepository.findByEmail(email) != null ? convertUserEmailToDTO(userRepository.findByEmail(email))
                : null;
    }

    private UserEmailDTO convertUserEmailToDTO(User user) {
        UserEmailDTO userEmailDTO = new UserEmailDTO();
        userEmailDTO.setEmail(user.getEmail());
        return userEmailDTO;
    }

    public User findById(Long id) {
        log.info("Searching user by id: " + id);
        return userRepository.findByIdUser(id);
    }

    public User findByUsername(String username) {
        log.info("Searching user by username: " + username);
        return userRepository.findByUsername(username) != null ? userRepository.findByUsername(username) : null;
    }

    public User findByUserEmail(String email) {
        log.info("Searching user by email: " + email);
        return userRepository.findByEmail(email) != null ? userRepository.findByEmail(email) : null;
    }

    public UsernameDTO findByUsernameValidation(String username) {
        log.info("Searching user by username: " + username);
        return userRepository.findByUsername(username) != null
                ? convertUsernameToDTO(userRepository.findByUsername(username))
                : null;
    }

    private UsernameDTO convertUsernameToDTO(User user) {
        UsernameDTO usernameDTO = new UsernameDTO();
        usernameDTO.setUsername(user.getUsername());
        return usernameDTO;
    }

    public Integer generateRandom() {
        Random r = new Random();
        int max = 99999;
        int min = 10000;
        return r.nextInt((max - min) + 1) + min;
    }

}
