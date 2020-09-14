package com.coupon.demo.service;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Coupon;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@ToString
@Service
public class CompanyService extends ClientService {

    private boolean isLoggedIn = false;
    @ToString.Exclude
    private Company company;

    @Autowired
    public CompanyService(CategoryRepository categoryRepository,
                          CompanyRepository companyRepository, CouponRepository couponRepository,
                          CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
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


    public void addCoupon(Coupon coupon) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Company is not logged in");
        }
        couponRepository.save(coupon);
        updateCompany();
    }

    public Coupon updateCoupon(Coupon coupon) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Company is not logged in");
        }
        return couponRepository.save(coupon);
    }

    @Transactional
    public void deleteCoupon(Coupon coupon) {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        couponRepository.deleteFromCvCByCouponId(coupon.getId());
        couponRepository.delete(coupon);
    }

    public List<Coupon> getCompanyCoupons() {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return couponRepository.findAllByCompany(company);
    }

    public List<Coupon> getCompanyCoupons(Category category) {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return couponRepository.findAllByCompanyAndCategory(company, category);
    }

    public List<Coupon> getCompanyCoupons(double maxPrice) {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return couponRepository.findAllByCompanyAndMaxPrice(company, maxPrice);
    }

    public Company getCompanyDetails() {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return companyRepository.findById(company.getId()).get();
    }

    private Company updateCompany() {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return company = companyRepository.findById(company.getId()).get();
    }
}
