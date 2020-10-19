package com.coupon.demo;

import com.coupon.demo.entities.*;
import com.coupon.demo.facade.AdminFacade;
import com.coupon.demo.facade.CompanyFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;


//TODO: Make customer and company facade into prototype

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
//        generateDefaultDatabase(context);


    }
    private static void generateDefaultDatabase(ConfigurableApplicationContext context) {
        AdminFacade adminFacade = context.getBean(AdminFacade.class);

        Company company1 = adminFacade
                .addCompany(
                        new Company("SwimFasst", "swim@email.com", "password")
                );
        Company company2 = adminFacade
                .addCompany(
                        new Company("HikerMountain", "hike@email.com", "password")
                );
        Company company3 = adminFacade
                .addCompany(
                        new Company("DirtBikes", "dirty@email.com", "password")
                );

        adminFacade
                .addCustomer(
                        new Customer("Jake","Karassik","jake@email.com", "password")
                );
        adminFacade
                .addCustomer(
                        new Customer("Dylan","Karassik","dylan@email.com", "password")
                );
        adminFacade
                .addCustomer(
                        new Customer("Omer","Desezer","omer@email.com", "password")
                );

        adminFacade
                .addCustomer(
                        new Customer("Alon","Shaul","alon@email.com", "password")
                );
        adminFacade
                .addCustomer(
                        new Customer("Freddy","goodenough","freddy@email.com", "password")
                );
        Category swimming = adminFacade.addCategory(new Category(CategoryType.SWIMMING));
        Category bikes = adminFacade.addCategory(new Category(CategoryType.BIKES));
        Category hiking = adminFacade.addCategory(new Category(CategoryType.HIKING));

        CompanyFacade companyFacade = context.getBean(CompanyFacade.class);

        companyFacade.addCoupon(new Coupon(
                swimming, company1, "Swim With Sharks", "Swim with " +
                "the great white sharks", LocalDate.now(), LocalDate.now().plusDays(5), 5, 89.99,
                "image"
                ));

        companyFacade.addCoupon(new Coupon(
                swimming, company1, "Swim With Dolphins", "Swim with " +
                "the little fry", LocalDate.now(), LocalDate.now().plusDays(5), 5,
                59.99,
                "image"
        ));

        companyFacade.addCoupon(new Coupon(
                swimming, company1, "Dive with scooby", "Never seen before",
                LocalDate.now(), LocalDate.now().plusDays(5), 5, 99.99,
                "image"
        ));

        companyFacade.addCoupon(new Coupon(
                bikes, company2, "Ride the dirty mountain", "At incredible speed",
                LocalDate.now(), LocalDate.now().plusDays(5), 5, 69.99,
                "image"
        ));
        companyFacade.addCoupon(new Coupon(
                bikes, company2, "Ride the local town", "At moderate speed",
                LocalDate.now(), LocalDate.now().plusDays(5), 5, 39.99,
                "image"
        ));

        companyFacade.addCoupon(new Coupon(
                hiking, company3, "Climb the freezing mountain", "With a professional",
                LocalDate.now(), LocalDate.now().plusDays(5), 5, 29.99,
                "image"
        ));

        companyFacade.addCoupon(new Coupon(
                hiking, company3, "Run downhill fast", "At a dangerous pace",
                LocalDate.now(), LocalDate.now().plusDays(5), 5, 79.99,
                "image"
        ));
    }

}
