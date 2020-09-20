package com.coupon.demo.dtobeans;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Data
public class CompanyDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Coupon> coupons;

}
