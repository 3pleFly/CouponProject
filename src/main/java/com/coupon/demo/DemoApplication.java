package com.coupon.demo;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Coupon;
import com.coupon.demo.beans.Customer;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import com.coupon.demo.service.AdminService;
import com.coupon.demo.service.CompanyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {


    // Inside adminFacade, in updateCompany, I have to check that the company code/name is not
    // being updated, since this is handled by the MYSQL entity conditions, do I need to catch an
    // exception here? If not will I still get a stackprinttrace? How to handle MYSQL Exceptions?

    //Inside companyFacade and CustomerFacade, when loggin in, why hold only the ID and not the
    // whole
    // company/customer object?

    //HOw to do logger?
    //changing the column length parameter is not updating MYSQL table...
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        AdminService adminFacade = context.getBean(AdminService.class);
        CompanyService companyFacade = context.getBean(CompanyService.class);
        CategoryRepository categoryRepository = context.getBean(CategoryRepository.class);
        CouponRepository couponRepository = context.getBean(CouponRepository.class);
        CustomerRepository customerRepository = context.getBean(CustomerRepository.class);
        CompanyRepository companyRepository = context.getBean(CompanyRepository.class);
        adminFacade.login("admin@admin.com", "admin");

        couponRepository.deleteFromCvCByCouponId(26L);
//
//    adminFacade.deleteCompany(6L);




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
//            Coupon coupon = new Coupon(category,oneCompany,title,description,startdate,endDate,amount
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
