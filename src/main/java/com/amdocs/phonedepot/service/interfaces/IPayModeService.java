/**
 * 
 */
package com.amdocs.phonedepot.service.interfaces;

import java.util.Collection;

import com.amdocs.phonedepot.model.PayMode;

/**
 * @author Dhanapal
 */
public interface IPayModeService {

	PayMode create(PayMode payMode);

	PayMode update(Long id, PayMode payMode);

	Boolean delete(Long idPayMode);

	Collection<PayMode> list();

	Boolean exist(Long idPayMode);

}
