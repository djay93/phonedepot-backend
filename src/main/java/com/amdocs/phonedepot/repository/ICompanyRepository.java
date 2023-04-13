package com.amdocs.phonedepot.repository;

import com.amdocs.phonedepot.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dhanapal
 */
@Repository
public interface ICompanyRepository  extends JpaRepository<Company, Long> {



}
