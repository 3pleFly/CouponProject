package com.coupon.demo.dto;

import com.coupon.demo.entities.Coupon;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyDTO {

    private Long id;
    private String name;
    private String email;
    private List<Coupon> coupons;

}
