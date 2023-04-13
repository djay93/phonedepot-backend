/**
 * 
 */
package com.amdocs.phonedepot.dto;

import java.util.Collection;

import lombok.Data;

/**
 * @author Dhanapal
 */
@Data
public class UserBillOrdersDTO {

	private BillUserDTO billUserDTO;

	private Collection<OrdersDTO> ordersDTO;

}
