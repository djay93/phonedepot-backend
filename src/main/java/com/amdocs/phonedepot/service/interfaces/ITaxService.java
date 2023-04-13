package com.amdocs.phonedepot.service.interfaces;

import com.amdocs.phonedepot.model.Tax;

import java.util.Collection;

/**
 * @author Dhanapal
 */
public interface ITaxService {
    Tax create(Tax tax);

    Collection<Tax> read();

    Tax update(Long idTax, Tax tax);

    Boolean delete(Long idTax);

    Boolean exist(Long idTax);
}
