package com.coupon.demo.dtobeans;

import com.coupon.demo.beans.Coupon;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Data
public class CustomerDTO {

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Coupon> coupons;


}
