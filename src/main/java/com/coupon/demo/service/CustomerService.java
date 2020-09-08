package com.coupon.demo.service;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.Coupon;
import com.coupon.demo.beans.Customer;
import com.coupon.demo.exception.CouponExpired;
import com.coupon.demo.exception.CouponNotAvailable;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService extends ClientService {

    private boolean isLoggedIn = false;
    Logger logger = LoggerFactory.getLogger("CustomerService Logger");
    private Customer customer;

    @Autowired
    public CustomerService(CategoryRepository categoryRepository,
                           CompanyRepository companyRepository, CouponRepository couponRepository,
                           CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
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
        if (customer.getCoupons().contains(coupon)) {
            throw new RuntimeException("Customer already purchased this coupon");
        }
        if (coupon.getAmount().equals(0)) {
            throw new CouponNotAvailable("The coupon's available amount is 0");
        }
        if (coupon.getEndDate().isAfter(LocalDate.now())) {
            throw new CouponExpired("The coupon's available amount is 0");
        }
        customer.getCoupons().add(coupon);
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepository.save(coupon);
        this.customer = customerRepository.save(customer);
    }

    public List<Coupon> getCustomerCoupons() {
        if (isLoggedIn == false) {
            throw new LoginFailed("Customer is not logged in");
        }
        return customerRepository.findById(customer.getId()).get().getCoupons();
    }

    public List<Coupon> getCustomerCoupons(Category category) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Customer is not logged in");
        }
        return couponRepository.findAllByCustomerAndCategory(customer, category);
    }

    public List<Coupon> getCustomerCoupons(double maxPrice) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Customer is not logged in");
        }
        return couponRepository.findAllByCustomerAndMaxPrice(customer.getId(), maxPrice);
    }

    public Customer getCustomerDetails() {
        if (isLoggedIn == false) {
            throw new LoginFailed("Customer is not logged in");
        }
        return customer;
    }
}

