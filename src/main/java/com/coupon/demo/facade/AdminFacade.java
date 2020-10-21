package com.coupon.demo.facade;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.service.dao.CategoryDao;
import com.coupon.demo.service.dao.CompanyDao;
import com.coupon.demo.service.dao.CustomerDao;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class AdminFacade {

    private CompanyDao companyDao;
//    private CouponDao couponDao;
    private CategoryDao categoryDao;
    private CustomerDao customerDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Company addCompany(Company company) {
        company.setPassword(bCryptPasswordEncoder.encode(company.getPassword()));
        return companyDao.addCompany(company);
    }

    public Company updateCompany(Company company) {
        company.setPassword(bCryptPasswordEncoder.encode(company.getPassword()));
        return companyDao.updateCompany(company);
    }

    @Transactional
    public void deleteCompany(Long companyId) {
        companyDao.deleteCompany(companyId);
    }

    public List<Company> getAllCompanies() {
        return companyDao.getAllCompanies();
    }

    public Company getOneCompany(Long companyId) {
        return companyDao.getOneCompany(companyId);
    }

    public Customer addCustomer(Customer customer) {
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        return customerDao.addCustomer(customer);
    }

    public Customer updateCustomer(Customer customer) {
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        return customerDao.updateCustomer(customer);
    }

    public void deleteCustomer(Long customerId) {
        customerDao.deleteCustomer(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    public Customer getOneCustomer(Long customerId) {
        return customerDao.getOneCustomer(customerId);
    }

    public Category addCategory(Category category) {
        return categoryDao.addCategory(category);
    }

    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

}
