package com.coupon.demo.exception;

public class BadToken extends RuntimeException {
    public BadToken(String message) {
        super(message);
    }
}
