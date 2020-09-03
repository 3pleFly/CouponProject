package com.coupon.demo.exception;

public class LoginFailed extends RuntimeException {
    public LoginFailed(String message) {
        super(message);
    }
}
