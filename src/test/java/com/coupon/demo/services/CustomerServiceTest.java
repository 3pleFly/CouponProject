package com.coupon.demo.services;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.Coupon;
import com.coupon.demo.facade.CustomerFacade;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.service.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerServiceTest {

    private Logger logger = LoggerFactory.getLogger("CustomerServiceTest Logger");
    private Coupon coupon;

    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    void login() {
        try {
            customerFacade.login("new@emailer.com", "password");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void purchaseCoupon() {
        try {
            login();
            coupon = couponRepository.findById(2L).get();
            customerFacade.purchaseCoupon(coupon);
            logger.info("purchaseCoupon Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void getCustomerCoupons() {
        try {
            login();
            customerFacade.getCustomerCoupons().forEach(System.out::println);
            logger.info("getCustomerCoupons Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void getCustomerCouponsByCategory() {
        try {
            login();
            Category category = categoryRepository.findById(2L).get();
            customerFacade.getCustomerCoupons(category).forEach(System.out::println);
            logger.info("getCustomerCouponsByCategory Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void getCustomerCouponsByMaxPrice() {
        try {
            login();
            customerFacade.getCustomerCoupons(49.99).forEach(System.out::println);
            logger.info("getCustomerCouponsByMaxPrice Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void getCustomerDetails() {
        try {
            login();
            System.out.println(customerFacade.getCustomerDetails());
            logger.info("getCustomerDetails Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
