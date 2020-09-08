package com.coupon.demo.services;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Coupon;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.service.AdminService;
import com.coupon.demo.service.ClientType;
import com.coupon.demo.service.CompanyService;
import com.coupon.demo.service.LoginManager;
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
    private CompanyService companyService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CouponRepository couponRepository;


    @Test
    void login() {
        companyService.login("4new@email.com", "password");
    }

    @Test
    void addCoupon() {
        login();
        coupon = createCoupon();
        companyService.addCoupon(coupon);
        logger.info("addCoupon Test");
    }

    @Test
    void updateCoupon() {
        login();
        coupon = companyService.getCompanyCoupons().get(0);
        coupon.setPrice(coupon.getPrice() * 2);
        companyService.updateCoupon(coupon);
        logger.info("updateCoupon Test");
    }

    @Test
    void deleteCoupon() {
        login();
        coupon = companyService.getCompanyCoupons().get(0);
        System.out.println(coupon);
        companyService.deleteCoupon(coupon);
    }

    @Test
    void getCompanyCoupons() {
        login();
        companyService.getCompanyCoupons().forEach(System.out::println);
        logger.info("getCompanyCoupons Test");
    }

    @Test
    void getCompanyCouponsByCategory() {
        login();
        Category category = categoryRepository.findById(1L).get();
        companyService.getCompanyCoupons(category).forEach(System.out::println);
        logger.info("getCompanyCouponsByCategory Test");
    }

    @Test
    void getCompanyCouponsByMaxPrice() {
        login();
        companyService.getCompanyCoupons(49.99).forEach(System.out::println);
        logger.info("getCompanyCouponsByMaxPrice Test");
    }

    @Test
    void getCompanyDetails() {
        login();
        System.out.println(companyService.getCompanyDetails());
        logger.info("getCompanyDetails Test");
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
        Company oneCompany = companyService.getCompanyDetails();

        return new Coupon(category, oneCompany, title, description, startdate, endDate,
                amount
                , price, image);
    }
}
