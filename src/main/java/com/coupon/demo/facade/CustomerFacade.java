package com.coupon.demo.facade;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.exception.CouponExpired;
import com.coupon.demo.exception.CouponNotAvailable;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.service.CustomerService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@ToString
@Service
public class CustomerFacade extends ClientFacade {

    private final CustomerService customerService;
    private boolean isLoggedIn = false;
    private Customer customer;

    @Autowired
    public CustomerFacade(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public boolean login(String email, String password) {
        customerService.isCustomerExists(email, password).ifPresent((loggedCustomer -> {
            customer = loggedCustomer;
            isLoggedIn = true;
        }));
        throw new LoginFailed("Admin login failed!");
    }

    public void purchaseCoupon(Coupon coupon) {
        if (!isLoggedIn) {
            throw new LoginFailed("Customer is not logged in");
        }
        if (coupon.getAmount().equals(0)) {
            throw new CouponNotAvailable("The coupon's available amount is 0");
        }
        if (coupon.getEndDate().isBefore(LocalDate.now())) {
            throw new CouponExpired("The coupon is expired");
        }
        if (customer.getCoupons().contains(coupon)) {
            throw new RuntimeException("Customer already purchased this coupon");
        }
        customerService.purchaseCoupon(coupon);
        customerService.getCustomerDetails(customer.getId());
    }

    public List<Coupon> getCustomerCoupons() {
        if (!isLoggedIn) {
            throw new LoginFailed("Customer is not logged in");
        }
        return customerService.getCustomerCoupons(customer);
    }

    public List<Coupon> getCustomerCoupons(Category category) {
        if (!isLoggedIn) {
            throw new LoginFailed("Customer is not logged in");
        }
        return customerService.getCustomerCoupons(category, customer);
    }

    public List<Coupon> getCustomerCoupons(double maxPrice) {
        if (!isLoggedIn) {
            throw new LoginFailed("Customer is not logged in");
        }
        return customerService.getCustomerCoupons(maxPrice, customer);
    }

    public Customer getCustomerDetails() {
        if (!isLoggedIn) {
            throw new LoginFailed("Customer is not logged in");
        }
        return customerService.getCustomerDetails(customer.getId());
    }

}
