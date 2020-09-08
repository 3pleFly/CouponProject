package com.coupon.demo.service;

import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Customer;
import com.coupon.demo.exception.AlreadyExists;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService extends ClientService {

    private boolean isLoggedIn = false;

    @Autowired
    public AdminService(CompanyRepository companyRepository,
                        CustomerRepository customerRepository, CouponRepository couponRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }

    @Override
    public boolean login(String email, String password) {
        if (email == "admin@admin.com" && password == "admin") {
            isLoggedIn = true;
        } else {
            throw new LoginFailed("Admin login failed!");
        }
        return isLoggedIn;
    }

    public Company addCompany(Company company) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return companyRepository.save(company);
    }

    public Company updateCompany(Company company) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return companyRepository.save(company);
    }

    @Transactional
    public void deleteCompany(Company company) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        couponRepository.deleteFromCvCByCompanyId(company.getId());
        couponRepository.deleteByCompany(company);
        companyRepository.delete(company);
    }

    public List<Company> getAllCompanies() {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return companyRepository.findAll();
    }

    public Company getOneCompany(Long companyId) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return companyRepository.findById(companyId).get();
    }

    public Customer addCustomer(Customer customer) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        customerRepository.deleteById(customerId);
    }

    public List<Customer> getAllCustomers() {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return customerRepository.findAll();
    }

    public Customer getOneCustomer(Long customerId) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return customerRepository.findById(customerId).get();
    }
}
