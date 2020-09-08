package com.coupon.demo.service;


import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {

    private AdminService adminService;
    private CompanyService companyService;
    private CustomerService customerService;

    public LoginManager() {
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public ClientService login(String email, String password, ClientType clientType) {
        switch (clientType) {
            case Administrator:
                adminService.login(email, password);
                return adminService;

            case Company:
                companyService.login(email, password);
                return companyService;

            case Customer:
                customerService.login(email, password);
                return customerService;
        }
        throw new RuntimeException("Login manager failed");
    }
}
