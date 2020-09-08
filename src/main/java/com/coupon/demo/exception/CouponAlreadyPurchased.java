package com.coupon.demo.exception;

public class CouponAlreadyPurchased extends RuntimeException{
    public CouponAlreadyPurchased(String message) {
        super(message);
    }
}
