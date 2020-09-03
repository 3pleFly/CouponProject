package com.coupon.demo.service;

import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Coupon;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService extends ClientService {

    CompanyRepository companyRepository;
    CustomerRepository customerRepository;
    CouponRepository couponRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository,
                        CustomerRepository customerRepository,
                        CouponRepository couponRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }
    private Long companyID;
    private boolean isLoggedIn = false;

    public CompanyService() {
    }

    @Override
    public boolean login(String email, String password) {
        if (companyRepository.existsByEmailAndPassword(email, password)) {
            isLoggedIn = true;
            companyID = companyRepository.findByEmailAndPassword(email, password).getId();
            return isLoggedIn;
        } else {
            throw new LoginFailed("Company login failed!");
        }
    }


    public Coupon addCoupon(Coupon coupon) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return couponRepository.save(coupon);
    }

    public Coupon updateCoupon(Coupon coupon) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return couponRepository.save(coupon);
    }

    public void deleteCoupon(Long couponId) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        couponRepository.deleteById(couponId);
    }

//    public List<Coupon> getCompanyCoupons(Coupon coupon) {
//        if (isLoggedIn == false) {
//            throw new LoginFailed("Admin is not logged in");
//        }
//    }
//
//    public List<Coupon> getCompanyCoupons(Category category) {
//        if (isLoggedIn == false) {
//            throw new LoginFailed("Admin is not logged in");
//        }
//    }
//
//    public List<Coupon> getCompanyCoupons(double maxPrice) {
//        if (isLoggedIn == false) {
//            throw new LoginFailed("Admin is not logged in");
//        }
//    }

    public Company getCompanyDetails() {
        return companyRepository.findById(companyID).get();
    }
}
