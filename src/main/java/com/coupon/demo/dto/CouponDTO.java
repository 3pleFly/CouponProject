package com.coupon.demo.dto;

import com.coupon.demo.entities.Category;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class CouponDTO {

    private Long id;
    private Category category;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer amount;
    private Double price;
    private String image;



}
