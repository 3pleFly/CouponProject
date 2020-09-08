package com.coupon.demo;

import com.coupon.demo.beans.*;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import com.coupon.demo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {

    //Can I inject repositories in services only once? Omer?

    // Inside adminFacade, in updateCompany, I have to check that the company code/name is not
    // being updated, since this is handled by the MYSQL entity conditions, do I need to catch an
    // exception here? If not will I still get a stackprinttrace? How to handle MYSQL Exceptions?

    //Inside companyFacade and CustomerFacade, when loggin in, why hold only the ID and not the
    // whole
    // company/customer object?

    //HOw to do logger?
    //changing the column length parameter is not updating MYSQL table...


    //Can I use company/customer instead of LoggedIn boolean?
    //ClientService field injection or constructor injection? How?
    // TODO: Change everything to Objects instead of primitives.
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("main logger");

        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

///////////////////
///* CREATE COUPON */
//
//
//        int i = 0;
//        System.out.println(
//                "hey"
//        );
//        while(i < 10) {
//            System.out.println("hey?");
//            String title = "title4" +i;
//            String description = "description";
//            LocalDate startdate = LocalDate.now();
//            LocalDate endDate = LocalDate.now();
//            int amount = 1;
//            double price = 29.99;
//            String image = "image";
//            Category category = categoryRepository.findById(1L).get();
//            Company oneCompany = adminFacade.getOneCompany(7L);
//
//            Coupon coupon = new Coupon(category,oneCompany,title,description,startdate,endDate,
//            amount
//                    ,price,image);
//            couponRepository.save(coupon);
//
//            i++;
//        }


//        //////////////////////
//        /* CREATE CUSTOMER */
//        String fname = "f";
//        String lname = "f";;
//        String cemail = "f";;
//        String cpassword = "f";;
//        Customer customer = new Customer(fname,lname,cemail,cpassword);
//        customerRepository.save(customer);
//
//        //////////////////////
//        /* CREATE COMPANY */
//        String Cname = "h";
//        String coemail = "h";
//        String copassword = "h";
//        Company company = new Company(Cname,coemail,copassword);
//        companyRepository.save(company);
//
//        //////////////////////
//        /* GIVE CUSTOMER A COUPON */
//        Customer customer = customerRepository.findById(5L).get();
//        customer.getCoupons().add(couponRepository.findById(25L).get());
//        customer.getCoupons().add(couponRepository.findById(26L).get());
//        customer.getCoupons().add(couponRepository.findById(27L).get());
//        customer.getCoupons().add(couponRepository.findById(28L).get());
//        customer.getCoupons().add(couponRepository.findById(29L).get());
//        customerRepository.save(customer);
    }


}
