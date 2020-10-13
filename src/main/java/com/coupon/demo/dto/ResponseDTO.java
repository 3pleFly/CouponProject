package com.coupon.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO<T> {

    private T t;
    private boolean success;
    private String message;

    @Override
    public String toString() {
        return "ResponseDTO{" +
                t.getClass() + "=" + t +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
