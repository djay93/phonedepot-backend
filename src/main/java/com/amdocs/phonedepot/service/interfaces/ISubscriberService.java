/**
 * 
 */
package com.amdocs.phonedepot.service.interfaces;

import java.util.Collection;

import com.amdocs.phonedepot.model.Subscriber;

/**
 * @author Dhanapal
 */
public interface ISubscriberService {
	
	Subscriber create(Subscriber subscriber);
	
	Collection<Subscriber> list(Long page);

	Boolean delete(Long idSubscriber);

	Boolean exist(Long idSubscriber);

	Boolean existByEmail(String email);
	
	Collection<Subscriber> searchByEmail(String email);


}
