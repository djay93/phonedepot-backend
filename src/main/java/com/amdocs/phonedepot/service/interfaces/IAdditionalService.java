/**
 * 
 */
package com.amdocs.phonedepot.service.interfaces;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

import com.amdocs.phonedepot.model.Additional;

/**
 * @author Dhanapal
 */
public interface IAdditionalService {

	Additional create(Additional additional, MultipartFile file);

	Additional update(Long id,Additional additional,MultipartFile file);

	Boolean delete(Long id_Additional);

	Collection<Additional> list();

	Boolean exist(Long id_Additional);
}
