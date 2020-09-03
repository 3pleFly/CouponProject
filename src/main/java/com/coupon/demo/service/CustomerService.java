package com.coupon.demo.service;

import org.springframework.stereotype.Service;

@Service
public class CustomerService extends ClientService {

    private boolean isLoggedIn = false;
    private Long customerID;

    public CustomerService() {
    }

    @Override
    public boolean login(String email, String password) {
        return false;
    }

//    public Coupon purchaseCoupon(Coupon coupon) {
//
//    }
//
//    public List<Coupon> getCustomerCoupons() {
//
//    }
//
//    public List<Coupon> getCustomerCoupons(Category category) {
//
//    }
//
//    public List<Coupon> getCustomerCoupons(double maxPrice) {
//
//    }
//
//    public Customer getCustomerDetails() {
//
//    }

}

