package com.coupon.demo.services;

import com.coupon.demo.service.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginManagerTest {

    @Autowired
    private LoginManager loginManager;

    private Logger logger = LoggerFactory.getLogger("LoginManagerTest Logger");
    private AdminService adminService;
    private CompanyService companyService;
    private CustomerService customerService;

    @Test
    void testAdminService() {
        try {
            adminService = (AdminService) loginManager.login("admin@admin.com", "admin",
                    ClientType.Administrator);
            logger.info(adminService.toString());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    @Test
    void testCompanyService() {
        try {
            companyService = (CompanyService) loginManager.login("new@email.com", "password",
                    ClientType.Company);
            logger.info(companyService.toString());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    @Test
    void testCustomerService() {
        try {
            customerService = (CustomerService) loginManager.login("New@Emailer.com", "password",
                    ClientType.Customer);
            logger.info(customerService.toString());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
