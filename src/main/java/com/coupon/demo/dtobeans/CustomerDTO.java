package com.coupon.demo.dtobeans;

import lombok.*;

@AllArgsConstructor
@Data
public class CustomerDTO {

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;


}
