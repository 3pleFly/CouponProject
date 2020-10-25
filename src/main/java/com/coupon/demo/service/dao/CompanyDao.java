package com.coupon.demo.service.dao;

import com.coupon.demo.entities.Company;
import com.coupon.demo.exception.*;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CompanyDao {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;

    public Company addCompany(Company company) {
        checkNull(company);
        if (companyRepository.findByEmail(company.getEmail()).isPresent()) {
            throw new AlreadyExists("Email already exists: " + company.getEmail());
        }
        if (companyRepository.findByName(company.getName()).isPresent()) {
            throw new AlreadyExists("Name already exists: " + company.getName());
        }
        if (company.getName().length() > 30
                || company.getEmail().length() > 100
                || company.getPassword().length() > 200) {
            throw new LengthException("The name, email, or password exceeds limit");
        }
        return companyRepository.save(company);
    }

    public Company updateCompany(Company company) {
        checkExists(company.getId());
        checkNull(company);
        if (!companyRepository.findById(company.getId()).get().getName().equals(company.getName())) {
            throw new BadUpdate("Cannot change company name: " + company.getName());
        }
        companyRepository.findAllByEmail((company.getEmail())).ifPresent(companies -> {
            companies.forEach(companyByEmail -> {
                if (!companyByEmail.getId().equals(company.getId())) {
                    throw new AlreadyExists("Email already exists: " + company.getName());
                }
            });
        });
        if (company.getEmail().length() > 100
                || company.getPassword().length() > 200) {
            throw new LengthException("The email or password exceeds limit");
        }
        return companyRepository.save(company);
    }

    public void deleteCompany(Long companyID) {
        checkExists(companyID);
        couponRepository.deleteFromCvCByCompanyId(companyID);
        couponRepository.deleteByCompany(companyRepository.findById(companyID).get());
        companyRepository.deleteById(companyID);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getOneCompany(Long companyID) {
        checkExists(companyID);
        return companyRepository.findById(companyID).get();
    }

    private void checkNull(Company company) {
        if (company.getName() == null
                || company.getEmail() == null
                || company.getPassword() == null) {
            throw new MissingAttributes("The name, email or password cannot be null");
        }
        if (company.getName().isBlank()
                || company.getEmail().isBlank()
                || company.getPassword().isBlank()) {
            throw new MissingAttributes("The name, email or password cannot be empty");
        }
    }

    private void checkExists(Long companyID) {
        if (!companyRepository.existsById(companyID)) {
            throw new NotFound("Company by ID not found: " + companyID);
        }
    }
}
