package com.coupon.demo.exception;

public class BadClaims extends RuntimeException {
    public BadClaims(String message) {
        super(message);
    }
}
