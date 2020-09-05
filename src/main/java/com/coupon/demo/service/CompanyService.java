package com.coupon.demo.service;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Coupon;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService extends ClientService {

    CompanyRepository companyRepository;
    CustomerRepository customerRepository;
    CouponRepository couponRepository;
    private Company company;
    private boolean isLoggedIn = false;
    @Autowired
    public CompanyService(CompanyRepository companyRepository,
                          CustomerRepository customerRepository,
                          CouponRepository couponRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }

    public CompanyService() {
    }

    @Override
    public boolean login(String email, String password) {
        if (companyRepository.existsByEmailAndPassword(email, password)) {
            isLoggedIn = true;
            company = companyRepository.findByEmailAndPassword(email, password);
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

    @Transactional
    public void deleteCoupon(Long couponId) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        couponRepository.deleteFromCvCByCouponId(couponId);
    }

    public List<Coupon> getCompanyCoupons() {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return couponRepository.findAllByCompany(company);
    }

    public List<Coupon> getCompanyCoupons(Category category) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return couponRepository.findAllByCompanyAndCategory(company, category);
    }

    public List<Coupon> getCompanyCoupons(double maxPrice) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Admin is not logged in");
        }
        return couponRepository.findAllByCompanyAndMaxPrice(company, maxPrice);
    }

    public Company getCompanyDetails() {
        return companyRepository.findById(company.getId()).get();
    }
}
