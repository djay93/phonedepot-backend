package com.amdocs.phonedepot.service;

import com.amdocs.phonedepot.model.Company;
import com.amdocs.phonedepot.repository.ICompanyRepository;
import com.amdocs.phonedepot.service.interfaces.ICompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * @author Dhanapal
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImp implements ICompanyService {

    @Autowired
    private final ICompanyRepository companyRepository;

    @Override
    public Company create(Company company) {
        log.info("Saving company: " + company.getName());
        return companyRepository.save(company);
    }

    @Override
    public Collection<Company> list() {
        log.info("List company");
        return companyRepository.findAll();
    }

    @Override
    public Company update(Long idCompany, Company company) {
        log.info("Updating company: " + company.getName());
        Company companyOld = companyRepository.findById(idCompany).get();
        company.setIdCompany(companyOld.getIdCompany());
        return companyRepository.save(company);
    }

    @Override
    public Boolean delete(Long idCompany) {
        log.info("Deleting company id: " + idCompany);
        companyRepository.deleteById(idCompany);
        return true;
    }

    @Override
    public Boolean exist(Long idCompany) {
        log.info("Searching company by id: " + idCompany);
        return companyRepository.existsById(idCompany);
    }
}
