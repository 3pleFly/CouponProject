package com.coupon.demo.dtobeans;

import lombok.*;

@AllArgsConstructor
@Data
public class CompanyDTO {

    private Long id;
    private String name;
    private String email;
    private String password;


}
