package com.coupon.demo.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Data
public class CustomerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<CouponDTO> coupons;

}
