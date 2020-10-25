package com.coupon.demo.dto;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Coupon;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class CouponDTO {

    private Long id;
    private Long categoryID;
    private Long companyID;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer amount;
    private Double price;
    private String image;


    public static List<CouponDTO> generateCouponDTO(List<Coupon> couponList) {
        List<CouponDTO> couponDTOs = new ArrayList<>();
        couponList.forEach(coupon -> {
            couponDTOs.add(new CouponDTO(
                    coupon.getId(),
                    coupon.getCompany().getId(),
                    coupon.getCategory().getId(),
                    coupon.getTitle(),
                    coupon.getDescription(),
                    coupon.getStartDate(),
                    coupon.getEndDate(),
                    coupon.getAmount(),
                    coupon.getPrice(),
                    coupon.getImage()));
        });
        return couponDTOs;
    }

    public static CouponDTO generateCouponDTO(Coupon coupon) {
        return new CouponDTO(
                coupon.getId(),
                coupon.getCompany().getId(),
                coupon.getCategory().getId(),
                coupon.getTitle(),
                coupon.getDescription(),
                coupon.getStartDate(),
                coupon.getEndDate(),
                coupon.getAmount(),
                coupon.getPrice(),
                coupon.getImage());
    }


}
