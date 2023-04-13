package com.amdocs.phonedepot.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Dhanapal
 */
@Getter
@AllArgsConstructor
public enum Status {

	INACTIVE(0),
	ACTIVE(1);
	private final int status;
}
