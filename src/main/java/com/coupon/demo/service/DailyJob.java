package com.coupon.demo.service;

import com.coupon.demo.beans.Coupon;
import com.coupon.demo.repositories.CouponRepository;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DailyJob implements Runnable{

    Logger logger = LoggerFactory.getLogger("FreddyLogger");

    private CouponRepository couponRepository;
    public static DailyJob instance = null;
    LocalTime deleteTime = LocalTime.of(16, 00);
    private boolean quit = false;

    private DailyJob(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public static DailyJob getInstance(CouponRepository couponRepository) {
        if (instance == null) {
            instance = new DailyJob(couponRepository);
        }
        return instance;
    }

    @Override
    public void run() {
        while (!quit) {
            if(LocalTime.now().truncatedTo(ChronoUnit.MINUTES).equals(deleteTime.truncatedTo(ChronoUnit.MINUTES))) {
//                List<Coupon> couponList = couponRepository.findAll();
//                couponList.forEach(coupon -> {
//                    if (coupon.getEndDate().isAfter(LocalDate.now())){
//                        couponRepository.deleteFromCvCByCouponId(coupon.getId());
//                        couponRepository.delete(coupon);
//                    }
//                });
            }
        }
    }

    public void stop() {
        quit = true;
    }

    public void now() {
        deleteTime = LocalTime.now();
    }
}
