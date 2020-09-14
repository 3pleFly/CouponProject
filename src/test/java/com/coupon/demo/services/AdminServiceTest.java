package com.coupon.demo.services;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.CategoryType;
import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Customer;
import com.coupon.demo.service.AdminService;
import com.coupon.demo.service.ClientType;
import com.coupon.demo.service.CompanyService;
import com.coupon.demo.service.LoginManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminServiceTest {


    @Autowired
    private AdminService adminService;

    private final Logger logger = LoggerFactory.getLogger("AdminServiceTest Logger");
    private Company company;
    private Customer customer;

    @Test
    void login() {
        try {
            adminService.login("admin@admin.com", "admin");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void addCompany() {
        try {
            login();
            company = new Company("New Company", "new@email.com", "password");
            adminService.addCompany(company);
            logger.info("addCompany Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void updateCompany() {
        try {
            login();
            company = adminService.getOneCompany(1L);
            company.setPassword("Hey I am different password");
            adminService.updateCompany(company);
            logger.info("updateCompany Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void deleteCompany() {
        try {
            login();
            adminService.deleteCompany(2L);
            logger.info("deleteCompany Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void getAllCompanies() {
        try {
            login();
            adminService.getAllCompanies().forEach(System.out::println);
            logger.info("getAllCompanies Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    @Test
    void getOneCompany() {
        try {
            login();
            System.out.println(adminService.getOneCompany(7L).toString());
            logger.info("getOneCompany Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void addCustomer() {
        try {
            login();
            customer = new Customer("New", "Customer", "New@Emailer.com", "password");
            adminService.addCustomer(customer);
            logger.info("addCustomer Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void updateCustomer() {
        try {
            login();
            customer = adminService.getOneCustomer(1L);
            customer.setFirstName("Updated Name");
            adminService.updateCustomer(customer);
            logger.info("updateCustomer Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void deleteCustomer() {
        try {
            login();
            adminService.deleteCustomer(1L);
            logger.info("deleteCustomer Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void getAllCustomers() {
        try {
            login();
            customer = adminService.getOneCustomer(customer.getId());
            adminService.getAllCustomers().forEach(System.out::println);
            logger.info("getAllCustomers Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    @Test
    void getOneCustomer() {
        try {
            login();
            System.out.println(adminService.getOneCustomer(2L).toString());
            logger.info("getOneCustomer Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void addCategory() {
        try {
            login();
            Category category = new Category(CategoryType.SWIMMING);
            adminService.addCategory(category);
            logger.info("addCategory Test");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
