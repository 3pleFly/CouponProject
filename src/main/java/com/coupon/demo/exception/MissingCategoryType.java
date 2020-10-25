package com.coupon.demo.exception;

public class MissingCategoryType extends RuntimeException {
    public MissingCategoryType(String message) {
        super(message);
    }
}
