/**
 * 
 */
package com.amdocs.phonedepot.dto.update;

import com.amdocs.phonedepot.enumeration.Status;
import lombok.Data;

/**
 * @author Dhanapal
 */
@Data
public class UserClientDTO {
	private String name;
	private String urlImage;
	private int phone;
	private String email;
	private String password;
	private Status status;
}
