package com.coupon.demo.service;

import com.coupon.demo.entities.Company;
import com.coupon.demo.repositories.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CompanyDao {

    private CompanyRepository companyRepository;

    public boolean isCompanyExists(String email, String password) {
        return companyRepository.existsByEmailAndPassword(email, password);
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }

    public void deleteCompany(Long companyId) {
        companyRepository.deleteById(companyId);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getOneCompany(Long id) {
        return companyRepository.findById(id).get();
    }

}
