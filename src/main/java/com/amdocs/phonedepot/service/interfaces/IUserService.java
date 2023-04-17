/**
 * 
 */
package com.amdocs.phonedepot.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import com.amdocs.phonedepot.dto.UserDTO;
import com.amdocs.phonedepot.dto.update.UserClientDTO;
import com.amdocs.phonedepot.dto.update.UserEmployeeDTO;
import com.amdocs.phonedepot.model.User;

/**
 * @author Dhanapal
 */
public interface IUserService {

	User create(User user, MultipartFile file);

    User updateOTP(User user, Long otp);

    boolean verifyEmail(User user, Long otp);

    UserDTO update(Long id, User user, MultipartFile file);

	UserDTO updateEmployee(UserEmployeeDTO userEmployeeDTO, long id);

	UserDTO updateClient(UserClientDTO userClientDTO, long id, MultipartFile file);

	Boolean delete(Long idUser);

	Boolean exist(Long idUser);

	Boolean exist(String username);

}
