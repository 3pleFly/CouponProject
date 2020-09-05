package com.coupon.demo.service;

import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Customer;
import com.coupon.demo.exception.AlreadyExists;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService extends ClientService {

    public boolean isLoggedIn = false;

    private  CouponRepository couponRepository;
    private  CompanyRepository companyRepository;
    private  CustomerRepository customerRepository;
    private  CategoryRepository categoryRepository;

    public AdminService(CouponRepository couponRepository, CompanyRepository companyRepository,
    CustomerRepository customerRepository,
    CategoryRepository categoryRepository) {
        this.couponRepository = couponRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
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
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        if (companyRepository.existsByNameOrEmail(company.getName(), company.getEmail())) {
            throw new AlreadyExists("AdminFacade: Company name/email already exists..." + company.getName() + " " + company.getEmail());
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
    public void deleteCompany(Long companyId) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        companyRepository.findById(companyId).ifPresent(company -> {
           company.getCoupons().forEach(coupon -> {
               coupon.getCustomer().forEach(customer -> {
                   customer.getCoupons().remove(coupon);
                   customerRepository.save(customer);
               });
           });
        });
        companyRepository.deleteById(companyId);
    }

    public List<Company> getAllCompanies(Company company) {
        if (isLoggedIn == false) {
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

    public List<Customer> getAllCustomers(Customer customer) {
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
