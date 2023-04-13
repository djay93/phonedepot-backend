/**
 * 
 */
package com.amdocs.phonedepot.dto;

import com.amdocs.phonedepot.enumeration.Status;
import lombok.Data;

/**
 * @author Dhanapal
 */
@Data
public class UserDTO {
	private Long idUser;
	private String name;
	private String username;
	private String urlImage;
	private int phone;
	private String email;
	private int discountPoint;
	private Status status;
}
