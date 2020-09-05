package com.coupon.demo.service;


import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {

    public static LoginManager instance = null;
    private static CouponRepository couponRepository;
    private static CompanyRepository companyRepository;
    private static CustomerRepository customerRepository;
    private static CategoryRepository categoryRepository;

    @Autowired
    public LoginManager(CouponRepository couponRepository, CompanyRepository companyRepository,
                        CustomerRepository customerRepository,
                        CategoryRepository categoryRepository) {
        this.couponRepository = couponRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager(couponRepository, companyRepository, customerRepository, categoryRepository);
        }
        return instance;
    }

    public ClientService login(String email, String password, ClientType clientType) {
        ClientService client;
        switch (clientType) {
            case Administrator:
                client = new AdminService(couponRepository, companyRepository, customerRepository, categoryRepository);
                if (client.login(email, password)) {
                    return client;
                }
                break;

            case Company:
                client = new CompanyService(couponRepository, companyRepository, customerRepository, categoryRepository);
                if (client.login(email, password)) {
                    return client;
                }
                break;

            case Customer:
                client = new CustomerService(couponRepository, companyRepository, customerRepository, categoryRepository);
                if (client.login(email, password)) {
                    return client;
                }
                break;
        }


        return null;

    }

}
