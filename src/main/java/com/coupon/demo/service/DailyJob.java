package com.coupon.demo.service;

import com.coupon.demo.service.dao.CouponDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@AllArgsConstructor
public class DailyJob implements Runnable {

    private final CouponDao couponDao;

    @Override
    public void run() {
        couponDao.getAllCoupons().forEach(coupon -> {
            if (coupon.getEndDate().isBefore(LocalDate.now())) {
                log.warn("Coupon expired!" + coupon);
              couponDao.deleteCoupon(coupon.getId());
            }
        });
    }
}
