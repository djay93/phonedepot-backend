/**
 * 
 */
package com.amdocs.phonedepot.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amdocs.phonedepot.model.User;
import com.amdocs.phonedepot.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.phonedepot.dto.UserDTO;
import com.amdocs.phonedepot.model.Response;
import com.amdocs.phonedepot.service.UserServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Dhanapal
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class RecoverPasswordController {

	@Autowired
	private final UserServiceImp serviceImp;

	@Autowired
	private final MailService mailService;

	@Value("${sendgrid.verification.link}")
	private String verificationLink;

	@GetMapping(value = "/recover-password")
	public ResponseEntity<Response> recoverPassword(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "email") String email) throws MessagingException, IOException {
		User user = serviceImp.findByUserEmail(email);
		if (user != null) {
			long otp = this.generateRandom();
			String verificationUrl = verificationLink + "?email=" + user.getEmail();
			serviceImp.updateOTP(user, otp);
			mailService.sendResetPasswordEmail("dhanapal.jayapandi@amdocs.com", user.getEmail(), user.getName(), verificationUrl, otp);
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("Mail sent").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).message("The email do not exist")
					.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

	@PostMapping("/reset-password")
	public ResponseEntity<Response> resetPassword(HttpServletRequest request,
			@RequestParam(name = "email") String email, @RequestParam(name = "otp") Long otp) {
		String newPassword = request.getParameter("newPassword");
		String repeatNewPassword = request.getParameter("repeatNewPassword");
		log.info("otp: " + otp);
		log.info("newPassword: " + newPassword);
		log.info("repeatNewPassword: " + repeatNewPassword);
		User user = serviceImp.findByUserEmail(email);
		if(user !=null) {
			boolean result = serviceImp.verifyEmail(user, otp);
			log.info("result: " + result);

			if (result && newPassword.equals(repeatNewPassword)) {
				User user1 = serviceImp.updatePasswordClient(user.getUsername(), newPassword);

				return ResponseEntity.ok(
						Response.builder().timeStamp(Instant.now())
								.data(Map.of("user", user1))
								.message("The user is verified.")
								.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
			} else {
				return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).message("The user is not verified")
						.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
			}
		}

		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.message("The user: " + email + " not exist!").status(HttpStatus.UNAUTHORIZED)
				.statusCode(HttpStatus.UNAUTHORIZED.value()).build());
	}

	public Integer generateRandom() {
		Random r = new Random();
		int max = 99999;
		int min = 10000;
		return r.nextInt((max - min) + 1) + min;
	}
}
