package com.coupon.demo.service;

import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class ClientService {

    protected CompanyRepository companyRepository;
    protected CustomerRepository customerRepository;
    protected CouponRepository couponRepository;
    protected CategoryRepository categoryRepository;

    public abstract boolean login(String email, String password);
}
