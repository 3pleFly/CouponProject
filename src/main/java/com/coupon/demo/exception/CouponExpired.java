package com.coupon.demo.exception;

public class CouponNotAvailable extends RuntimeException {
    public CouponNotAvailable(String message) {
        super(message);
    }
}
