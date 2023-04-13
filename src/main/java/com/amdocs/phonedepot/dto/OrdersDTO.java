/**
 * 
 */
package com.amdocs.phonedepot.dto;

import java.util.Collection;

import com.amdocs.phonedepot.enumeration.StatusOrder;
import com.amdocs.phonedepot.model.Product;
import lombok.Data;

/**
 * @author Dhanapal
 */
@Data
public class OrdersDTO {

//	Order
	private Long idOrder;
	private StatusOrder statusOrder;
	private int amount;
	private int total;

//	Product
	private Product product;
	
//	Additional
	private Collection<com.amdocs.phonedepot.model.Additional> Additional;
}
