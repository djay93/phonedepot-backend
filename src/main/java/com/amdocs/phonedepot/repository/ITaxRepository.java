package com.amdocs.phonedepot.repository;

import com.amdocs.phonedepot.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dhanapal
 */
@Repository
public interface ITaxRepository extends JpaRepository<Tax, Long> {
}
