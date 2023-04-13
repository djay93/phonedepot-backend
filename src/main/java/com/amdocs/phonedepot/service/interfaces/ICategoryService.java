/**
 * 
 */
package com.amdocs.phonedepot.service.interfaces;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

import com.amdocs.phonedepot.model.Category;

/**
 * @author Dhanapal
 */
public interface ICategoryService {

    Category create(Category category, MultipartFile file);

    Category update(Long id,Category category, MultipartFile file);

    Boolean delete(Long idCategory);

    Collection<Category> list();

    Boolean exist(Long idCategory);

}
