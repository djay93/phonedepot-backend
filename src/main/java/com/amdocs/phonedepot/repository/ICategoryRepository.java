/**
 * 
 */
package com.amdocs.phonedepot.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.phonedepot.model.Category;

/**
 * @author Dhanapal
 */
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
	Collection<Category> findAllByNameStartsWith(String name);

	Category findByName(String name);
}
