package com.amdocs.phonedepot.service.interfaces;

import com.amdocs.phonedepot.model.Company;

import java.util.Collection;

/**
 * @author Dhanapal
 */
public interface ICompanyService {

    Company create(Company company);

    Collection<Company> list();

    Company update(Long idCompany, Company company);

    Boolean delete(Long idCompany);

    Boolean exist(Long idCompany);

}
