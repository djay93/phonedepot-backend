/**
 * 
 */
package com.amdocs.phonedepot.dto.validation;

import lombok.Data;

/**
 * @author Dhanapal
 */
@Data
public class UserForBillDTO {
	private Long idUser;
	private String urlImage;
	private String username;
	private String name;
}
