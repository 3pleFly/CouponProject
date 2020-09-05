package com.coupon.demo.service;


import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {

    public static LoginManager instance = null;

    @Autowired
    private LoginManager() {

    }

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    public ClientService login(String email, String password, ClientType clientType) {
        ClientService client;
        switch (clientType) {
            case Administrator:
                client = new AdminService();
                if (client.login(email, password)) {
                    return client;
                }
                break;

            case Company:
                client = new CompanyService();
                if (client.login(email, password)) {
                    return client;
                }
                break;

            case Customer:
                client = new CustomerService();
                if (client.login(email, password)) {
                    return client;
                }
                break;
        }


        return null;

    }

}
