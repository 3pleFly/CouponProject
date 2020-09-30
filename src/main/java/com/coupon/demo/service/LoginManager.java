package com.coupon.demo.service;


import com.coupon.demo.dtobeans.Login;
import com.coupon.demo.facade.AdminFacade;
import com.coupon.demo.facade.ClientFacade;
import com.coupon.demo.facade.CompanyFacade;
import com.coupon.demo.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {

    private AdminFacade adminFacade;
    private CompanyFacade companyFacade;
    private CustomerFacade customerFacade;

    @Autowired
    public void setAdminService(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    @Autowired
    public void setCompanyFacade(CompanyFacade companyFacade) {
        this.companyFacade = companyFacade;
    }

    @Autowired
    public void setCustomerFacade(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    public ClientFacade login(Login login) {
        if (login.getEmail() != null && login.getPassword() != null && login.getClientType() != null) {
            ClientType clientType = login.getClientType();
            String email = login.getEmail();
            String password = login.getPassword();
            return getClientFacade(clientType, email, password);
        }
        throw new RuntimeException("Login information cannot be null!");
    }

    private ClientFacade getClientFacade(ClientType clientType, String email, String password) {
        switch (clientType) {
            case Administrator:
                adminFacade.login(email, password);
                return adminFacade;

            case Company:
                companyFacade.login(email, password);
                return companyFacade;

            case Customer:
                customerFacade.login(email, password);
                return customerFacade;
        }
        throw new RuntimeException("Login manager failed");
    }
}
