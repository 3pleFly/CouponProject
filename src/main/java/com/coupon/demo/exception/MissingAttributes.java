package com.coupon.demo.exception;

public class MissingAttributes extends RuntimeException {
    public MissingAttributes(String message) {
        super(message);
    }
}
