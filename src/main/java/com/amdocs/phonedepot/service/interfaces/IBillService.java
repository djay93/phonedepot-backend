/**
 * 
 */
package com.amdocs.phonedepot.service.interfaces;

import com.amdocs.phonedepot.dto.BillUserDTO;
import com.amdocs.phonedepot.dto.UserBillOrdersDTO;
import com.amdocs.phonedepot.enumeration.StatusBill;
import com.amdocs.phonedepot.enumeration.StatusOrder;
import com.amdocs.phonedepot.model.Bill;

import java.util.Collection;

/**
 * @author Dhanapal
 */
public interface IBillService {

	BillUserDTO create(Bill bill);

	UserBillOrdersDTO findByIdBill(Long idBill);

	Collection<UserBillOrdersDTO> findByNewIdUser(String username, StatusBill statusBill, String startDate, String endDate);

	Collection<UserBillOrdersDTO> findByOrder(StatusOrder statusOrder, String startDate, String endDate);
	
	BillUserDTO update(Long idBill, Bill bill);

	BillUserDTO updateStatusBill(Long idBill, StatusBill statusBill);

	Boolean delete(Long idBill);

	Boolean exist(Long idBill);
}
