/**
 * 
 */
package com.amdocs.phonedepot.dto;
import lombok.Data;

/**
 * @author Dhanapal
 */
@Data
public class ReportProductDTO {

	private Long idProduct;
	private String name;
	private double amount;
	private double total;
}
