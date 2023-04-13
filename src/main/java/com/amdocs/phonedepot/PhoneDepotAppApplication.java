package com.amdocs.phonedepot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.cache.annotation.EnableCaching;

import com.amdocs.phonedepot.service.CloudinaryService;

@SpringBootApplication()
@EnableCaching
public class PhoneDepotAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneDepotAppApplication.class, args);
	}
	
//	set the encryptor 
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CloudinaryService cloudinaryService() {
		return new CloudinaryService();
	}
}
