package com.coupon.demo.services;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.facade.CompanyFacade;
import com.coupon.demo.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class CompanyServiceTest {

    private Logger logger = LoggerFactory.getLogger("CompanyServiceTest Logger");
    private Coupon coupon;

    @Autowired
    private CompanyFacade companyFacade;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void login() {
        try {
            companyFacade.login("new@email.com", "password");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void addCoupon() {
        try {
            login();
            coupon = createCoupon();
            companyFacade.addCoupon(coupon);
            logger.info("addCoupon Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void updateCoupon() {
        try {
            login();
            coupon = companyFacade.getCompanyCoupons().get(0);
            coupon.setPrice(coupon.getPrice() * 2);
            companyFacade.updateCoupon(coupon);
            logger.info("updateCoupon Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void deleteCoupon() {
        try {
            login();
            coupon = companyFacade.getCompanyCoupons().get(0);
            companyFacade.deleteCoupon(coupon);
            logger.info("deleteCoupon Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void getCompanyCoupons() {
        try {
            login();
            companyFacade.getCompanyCoupons().forEach(System.out::println);
            logger.info("getCompanyCoupons Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void getCompanyCouponsByCategory() {
        try {
            login();
            Category category = categoryRepository.findById(1L).get();
            companyFacade.getCompanyCoupons(category).forEach(System.out::println);
            logger.info("getCompanyCouponsByCategory Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void getCompanyCouponsByMaxPrice() {
        try {
            login();
            companyFacade.getCompanyCoupons(49.99).forEach(System.out::println);
            logger.info("getCompanyCouponsByMaxPrice Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void getCompanyDetails() {
        try {
            login();
            System.out.println(companyFacade.getCompanyDetails());
            logger.info("getCompanyDetails Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Coupon createCoupon() {
        String title = "titler1";
        String description = "description";
        LocalDate startdate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        int amount = 1;
        double price = 29.99;
        String image = "image";
        Category category = categoryRepository.findById(1L).get();
        Company oneCompany = companyFacade.getCompanyDetails();

        return new Coupon(category, oneCompany, title, description, startdate, endDate,
                amount
                , price, image);
    }
}
