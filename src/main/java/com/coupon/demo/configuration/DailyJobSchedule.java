package com.coupon.demo.configuration;

import com.coupon.demo.service.DailyJob;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class DailyJobSchedule {

    private final DailyJob dailyJob;

    /**
     * Once a week(Sundays) retrieves all coupons from database and checks each one's expiration date(endDate). Coupon is deleted if it is past
     * expiry.
     */
    @Scheduled(cron = "${dailyJobTime}")
    public void startDailyJob() {
        Thread thread = new Thread(dailyJob);
        thread.start();
    }
}
