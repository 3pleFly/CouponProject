package com.coupon.demo.service;

import com.coupon.demo.entities.Coupon;
import com.coupon.demo.repositories.CouponRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Service
public class DailyJob implements Runnable {

    private final Logger logger = LoggerFactory.getLogger("DailyJob Logger");
    private CouponRepository couponRepository;
    private boolean quit = false;
    private LocalTime checkTime = LocalTime.of(16, 00);
    private List<Coupon> deletedCoupons = new ArrayList<>();

    public DailyJob(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Autowired
    public void setCouponRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void run() {
        while (!quit) {
            if (checkTime.getHour() == LocalTime.now().getHour() && checkTime.getMinute() == LocalTime.now().getMinute()) {
                logger.info("checking...");
                List<Coupon> couponList = couponRepository.findAll();
                couponList.forEach(coupon -> {
                    if (coupon.getEndDate().isBefore(LocalDate.now())) {
                        logger.info("Deleting expired coupons " + coupon.getId() + coupon.getTitle());
                        deletedCoupons.add(coupon);
                        couponRepository.deleteFromCvCByCouponId(coupon.getId());
                        couponRepository.delete(coupon);
                    }
                });
            }
            logger.info(deletedCoupons.toString());
        }
        logger.info(deletedCoupons.toString());
        logger.info("Quit");
    }

    public void quit() {
        quit = true;
    }

    public void now() {
        this.checkTime = LocalTime.now();
    }
}
