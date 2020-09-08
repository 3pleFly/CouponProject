package com.coupon.demo.exception;

public class CouponExpired extends RuntimeException {
    public CouponExpired(String message) {
        super(message);
    }
}
