/**
 * 
 */
package com.amdocs.phonedepot.enumeration;

/**
 * @author Dhanapal
 */
import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
	ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_CLIENT,ROLE_UNATTRIBUTED;

	public String getAuthority() {
		return name();
	}

}