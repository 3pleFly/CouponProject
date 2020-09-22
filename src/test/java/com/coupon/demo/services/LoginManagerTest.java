package com.coupon.demo.services;

import com.coupon.demo.dtobeans.Login;
import com.coupon.demo.facade.AdminFacade;
import com.coupon.demo.facade.CompanyFacade;
import com.coupon.demo.facade.CustomerFacade;
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
    private AdminFacade adminFacade;
    private CompanyFacade companyFacade;
    private CustomerFacade customerFacade;

    @Test
    void testAdminService() {
        try {
            Login login = new Login("admin@admin.com", "admin",
                    ClientType.Administrator);
            adminFacade = (AdminFacade) loginManager.login(login);
            logger.info(adminFacade.toString());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    @Test
    void testCompanyService() {
        try {
            Login login = new Login("new@email.com", "password",
                    ClientType.Company);

            companyFacade = (CompanyFacade) loginManager.login(login);
            logger.info(companyFacade.toString());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    @Test
    void testCustomerService() {
        try {
            Login login = new Login("New@Emailer.com", "password",
                    ClientType.Customer);

            customerFacade = (CustomerFacade) loginManager.login(login);
            logger.info(customerFacade.toString());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
