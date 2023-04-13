/**
 * 
 */
package com.amdocs.phonedepot.security;

/**
 * @author Dhanapal
 */
public final class OperationUtil {

	private static final String KEYWORD = "PHONEDEPOT_22";

	private OperationUtil() {
		throw new java.lang.UnsupportedOperationException("Utility class and cannot be instantiated");
	}

	public static String keyValue() {
		return KEYWORD;
	}
}
