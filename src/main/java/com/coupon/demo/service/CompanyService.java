package com.coupon.demo.service;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Coupon;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService extends ClientService {

    public boolean isLoggedIn = false;
    Logger logger = LoggerFactory.getLogger(AdminService.class);
    private Company company;

    private  CouponRepository couponRepository;
    private  CompanyRepository companyRepository;
    private  CustomerRepository customerRepository;
    private  CategoryRepository categoryRepository;

    public CompanyService(CouponRepository couponRepository, CompanyRepository companyRepository,
                           CustomerRepository customerRepository,
                           CategoryRepository categoryRepository) {
        this.couponRepository = couponRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean login(String email, String password) {

        Optional<Company> byId = companyRepository.findById(7L);
        logger.info(byId.get().toString());

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
