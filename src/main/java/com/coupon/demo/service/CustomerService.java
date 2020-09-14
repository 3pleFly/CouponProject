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
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.util.List;

@ToString
@Service
public class CustomerService extends ClientService {

    private boolean isLoggedIn = false;
    @ToString.Exclude
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
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        if (customer.getCoupons().contains(coupon)) {
            throw new RuntimeException("Customer already purchased this coupon");
        }
        if (coupon.getAmount().equals(0)) {
            throw new CouponNotAvailable("The coupon's available amount is 0");
        }
        if (coupon.getEndDate().isBefore(LocalDate.now())) {
            throw new CouponExpired("The coupon is expired");
        }
        customer.getCoupons().add(coupon);
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepository.save(coupon);
        this.customer = customerRepository.save(customer);
    }

    public List<Coupon> getCustomerCoupons() {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return customerRepository.findById(customer.getId()).get().getCoupons();
    }

    public List<Coupon> getCustomerCoupons(Category category) {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return couponRepository.findAllByCustomerAndCategory(customer, category);
    }

    public List<Coupon> getCustomerCoupons(double maxPrice) {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return couponRepository.findAllByCustomerAndMaxPrice(customer.getId(), maxPrice);
    }

    public Customer getCustomerDetails() {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return customer;
    }
}

