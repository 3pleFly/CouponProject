package com.coupon.demo.services;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.service.DailyJob;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class DailyJobTest {

    @Autowired
    DailyJob dailyJob;
    private Logger logger = LoggerFactory.getLogger("DailyJob Logger");
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepository companyRepository;


    @Test
    void startDailyJob() {
        try {
            Thread thread = new Thread(dailyJob);
            thread.start();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Adds a coupon with a past end date.
     * dailyJob.Now() sets dailyJob's check time to be this minute.
     * dailyJob begins deleting everything expired for 1 minute.
     * After 5 seconds of sleep quit() is called to end dailyJob.
     * dailyJob prints all the deleted coupons.
     */

    @Test
    void deleteExpiredCoupons() {
        try {
            startDailyJob();
            couponRepository.save(createCoupon());
            dailyJob.now();
            Thread.sleep(5000);
            dailyJob.quit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Coupon createCoupon() {
        String title = "Expired Coupon";
        String description = "description";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(5L);
        int amount = 1;
        double price = 29.99;
        String image = "image";
        Category category = categoryRepository.findById(1L).get();
        Company company = companyRepository.findById(1L).get();

        return new Coupon(category, company, title, description, startDate, endDate,
                amount
                , price, image);
    }
}
