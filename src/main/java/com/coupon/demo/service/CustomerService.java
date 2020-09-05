package com.coupon.demo.service;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.Coupon;
import com.coupon.demo.beans.Customer;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService extends ClientService {

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public boolean isLoggedIn = false;
    private Customer customer;

    public CustomerService() {
    }

    private CouponRepository couponRepository;
    private CompanyRepository companyRepository;
    private CustomerRepository customerRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCouponRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean login(String email, String password) {
        if (customerRepository.existsByEmailAndPassword(email, password)) {
            isLoggedIn = true;
            customer = customerRepository.findByEmailAndPassword(email, password);
            return isLoggedIn;
        } else {
            throw new LoginFailed("Customer login failed!");
        }
    }

    public void purchaseCoupon(Coupon coupon) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Customer is not logged in");
        }
        customer.getCoupons().add(coupon);
        logger.info(customer.getCoupons().toString());
        customerRepository.save(customer);
    }

    public List<Coupon> getCustomerCoupons() {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
//TODO: What is a simpler way to to write this V
        return customerRepository.findById(customer.getId()).get().getCoupons();
    }

    public List<Coupon> getCustomerCoupons(Category category) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return couponRepository.findAllByCustomerAndCategory(customer, category);
    }

    public List<Coupon> getCustomerCoupons(double maxPrice) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return couponRepository.findAllByCustomerAndMaxPrice(customer, maxPrice);
    }

    public Customer getCustomerDetails() {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return customerRepository.findById(customer.getId()).get();
    }

}

