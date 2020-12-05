package com.coupon.demo.dto;

import com.coupon.demo.model.entities.Coupon;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class CouponDTO {

    private Long id;
    private String categoryName;
    private Long companyID;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer amount;
    private Double price;
    private String image;

}
