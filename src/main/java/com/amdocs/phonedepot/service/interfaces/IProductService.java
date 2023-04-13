/**
 * 
 */
package com.amdocs.phonedepot.service.interfaces;
import org.springframework.web.multipart.MultipartFile;

import com.amdocs.phonedepot.model.Product;

/**
 * @author Dhanapal
 */
public interface IProductService {

	Product create(Product product, MultipartFile file);

	Product update(Long id, Product product, MultipartFile file);

	Boolean delete(Long idProduct);

	Boolean exist(Long idProduct);

}
