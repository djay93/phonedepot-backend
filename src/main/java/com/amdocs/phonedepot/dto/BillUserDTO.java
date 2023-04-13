/**
 * 
 */
package com.amdocs.phonedepot.dto;

import java.util.Date;

import com.amdocs.phonedepot.dto.validation.UserForBillDTO;
import com.amdocs.phonedepot.enumeration.StatusBill;
import com.amdocs.phonedepot.model.PayMode;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author Dhanapal
 */
@Data
public class BillUserDTO {
	private Long idBill;
	private String idTransaction;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date date;
	private int noTable;
	private int totalPrice;
	private StatusBill statusBill;
	private PayMode payMode;
	private UserForBillDTO userForBill;
	
}
