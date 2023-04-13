/**
 * 
 */
package com.amdocs.phonedepot.service.interfaces;


import com.amdocs.phonedepot.dto.OrdersDTO;
import com.amdocs.phonedepot.enumeration.StatusOrder;
import com.amdocs.phonedepot.model.Orders;

/**
 * @author Dhanapal
 */
public interface IOrdersService {

	OrdersDTO create(Orders orders);

	OrdersDTO update(Long id, Orders orders);

	Boolean updateStatus(Long idBill, StatusOrder statusOrder);
	Boolean delete(Long idOrders);

	Boolean exist(Long idOrders);

}
