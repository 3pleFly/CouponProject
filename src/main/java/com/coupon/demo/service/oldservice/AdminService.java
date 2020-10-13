package com.coupon.demo.service;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@ToString
@Service
public class AdminService {

    private boolean isLoggedIn = false;

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public AdminService(CompanyRepository companyRepository,
                        CustomerRepository customerRepository, CouponRepository couponRepository,
                        CategoryRepository categoryRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
        this.categoryRepository = categoryRepository;
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Company company) {
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

    public Company getOneCompany(Long companyId) {
        return companyRepository.findById(companyId).get();
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOneCustomer(Long customerId) {
        return customerRepository.findById(customerId).get();
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }
}
