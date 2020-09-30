package com.coupon.demo.facade;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminFacade extends ClientFacade{

    private AdminService adminService;
    private boolean isLoggedIn = false;

    @Autowired
    public AdminFacade(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public boolean login(String email, String password) {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            isLoggedIn = true;
        } else {
            throw new LoginFailed("Admin login failed!");
        }
        return isLoggedIn;
    }

    public Company addCompany(Company company) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return adminService.addCompany(company);
    }

    public Company updateCompany(Company company) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return adminService.updateCompany(company);
    }

    @Transactional
    public void deleteCompany(Long companyId) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        adminService.deleteCompany(companyId);
    }

    public List<Company> getAllCompanies() {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return adminService.getAllCompanies();
    }

    public Company getOneCompany(Long companyId) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return adminService.getOneCompany(companyId);
    }

    public Customer addCustomer(Customer customer) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return adminService.addCustomer(customer);
    }

    public Customer updateCustomer(Customer customer) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return adminService.updateCustomer(customer);
    }

    public void deleteCustomer(Long customerId) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        adminService.deleteCustomer(customerId);
    }

    public List<Customer> getAllCustomers() {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return adminService.getAllCustomers();
    }

    public Customer getOneCustomer(Long customerId) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return adminService.getOneCustomer(customerId);
    }

    public Category addCategory(Category category) {
        if (!isLoggedIn) {
            throw new LoginFailed("Admin is not logged in");
        }
        return adminService.addCategory(category);
    }


}
