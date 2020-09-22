package com.coupon.demo.service;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Customer;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ToString
@Service
public class AdminService extends ClientService {

    private boolean isLoggedIn = false;

    @Autowired
    public AdminService(CompanyRepository companyRepository,
                        CustomerRepository customerRepository, CouponRepository couponRepository,
                        CategoryRepository categoryRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean login(String email, String password) {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
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
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return companyRepository.save(company);
    }

    @Transactional
    public void deleteCompany(Long companyId) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        couponRepository.deleteFromCvCByCompanyId(companyId);
        couponRepository.deleteByCompany(companyRepository.findById(companyId).get());
        companyRepository.deleteById(companyId);
    }

    public List<Company> getAllCompanies() {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return companyRepository.findAll();
    }

    public Company getOneCompany(Long companyId) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return companyRepository.findById(companyId).get();
    }

    public Customer addCustomer(Customer customer) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        customerRepository.deleteById(customerId);
    }

    public List<Customer> getAllCustomers() {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return customerRepository.findAll();
    }

    public Customer getOneCustomer(Long customerId) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return customerRepository.findById(customerId).get();
    }

    public Category addCategory(Category category) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return categoryRepository.save(category);
    }
}
