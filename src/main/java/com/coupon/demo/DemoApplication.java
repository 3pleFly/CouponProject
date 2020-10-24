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

}
