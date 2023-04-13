/**
 * 
 */
package com.amdocs.phonedepot.service;

import java.util.Collection;

import javax.transaction.Transactional;

import com.amdocs.phonedepot.repository.ISubscriberRepository;
import com.amdocs.phonedepot.service.interfaces.ISubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amdocs.phonedepot.model.Subscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dhanapal
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SubscriberServiceImp implements ISubscriberService {

	@Autowired
	private ISubscriberRepository subscriberRepository;

	public Subscriber create(Subscriber subscriber) {
		log.info("Saving new subscriber: " + subscriber.getEmail());
		return subscriberRepository.save(subscriber);
	}

	@Override
	public Collection<Subscriber> list(Long page) {
		log.info("List subscriber");
		return subscriberRepository.list(page);
	}

	@Override
	public Boolean delete(Long idSubscriber) {
		log.info("Delete subscriber with id: " + idSubscriber);
		subscriberRepository.deleteById(idSubscriber);
		return true;
	}

	@Override
	public Boolean exist(Long idSubscriber) {
		log.info("Searching subscriber with id: " + idSubscriber);
		return subscriberRepository.existsById(idSubscriber);
	}

	@Override
	public Boolean existByEmail(String email) {
		log.info("Searching subscriber with email: " + email);
		return subscriberRepository.findByEmail(email) != null;
	}
	
	@Override
	public Collection<Subscriber> searchByEmail(String email) {
		log.info("Searching subscriber with email: " + email);
		return subscriberRepository.findByEmailStartsWith(email);
	}

}
