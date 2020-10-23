package com.coupon.demo.service.dao;

import com.coupon.demo.entities.Company;
import com.coupon.demo.exception.AlreadyExists;
import com.coupon.demo.exception.BadUpdate;
import com.coupon.demo.exception.NotFound;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CompanyDao {

    private CompanyRepository companyRepository;
    private CouponRepository couponRepository;

    public boolean isCompanyExists(String email, String password) {
        return companyRepository.existsByEmailAndPassword(email, password);
    }

    public Company addCompany(Company company) {
        if (companyRepository.findByEmail(company.getEmail()).isPresent()) {
            throw new AlreadyExists("Email already exists: " + company.getEmail());
        }
        if (companyRepository.findByName(company.getName()).isPresent()) {
            throw new AlreadyExists("Name already exists: " + company.getName());
        }
        return companyRepository.save(company);
    }


    public Company updateCompany(Company company) {
        if (!companyRepository.findById(company.getId()).get().getName().equals(company.getName())) {
            throw new BadUpdate("Cannot change company name" + company.getName());
        }
        return companyRepository.save(company);
    }

    public void deleteCompany(Long companyId) {
        couponRepository.deleteFromCvCByCompanyId(companyId);
        couponRepository.deleteByCompany(companyRepository.findById(companyId).get());
        companyRepository.deleteById(companyId);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getOneCompany(Long id) {
        try {
            return companyRepository.findById(id).get();
        } catch (NotFound e) {
            throw new NotFound("Company by ID:" + id + " was not found.");
        }
    }

}
