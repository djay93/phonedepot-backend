/**
 * 
 */
package com.amdocs.phonedepot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.phonedepot.model.PayMode;

/**
 * @author Dhanapal
 */
@Repository
public interface IPayModeRepository extends JpaRepository<PayMode, Long>{
}
