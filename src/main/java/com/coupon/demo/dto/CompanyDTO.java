package com.coupon.demo.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyDTO {

    private Long id;
    private String name;
    private String email;
    private List<CouponDTO> coupons;

}
