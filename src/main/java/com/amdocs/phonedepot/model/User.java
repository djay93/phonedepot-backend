package com.amdocs.phonedepot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.amdocs.phonedepot.enumeration.AppUserRole;
import com.amdocs.phonedepot.enumeration.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Dhanapal
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_app")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUser;
	@NotNull(message = "name cannot be empty or null")
	@Column(length = 50)
	private String name;
	@NotNull(message = "username cannot be empty or null")
	@Column(length = 50,unique = true)
	private String username;
	private String urlImage;
	@Column(length = 15)
	private int phone;
	@NotNull(message = "email cannot be empty or null")
	@Column(length = 50)
	private String email;
	@NotNull(message = "password cannot be empty or null")
	private String password;
	private int discountPoint;

	@Column(name = "otp")
	private Long otp;

	@Column(name = "expiry_time")
	private LocalDateTime expiryTime;

	private AppUserRole userRoles;

	private Status status;

}
