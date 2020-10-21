package com.coupon.demo.dto;

import com.coupon.demo.entities.Coupon;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Data
public class CustomerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Coupon> coupons;

}
