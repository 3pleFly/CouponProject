package com.coupon.demo.dtobeans;

import com.coupon.demo.beans.Category;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
