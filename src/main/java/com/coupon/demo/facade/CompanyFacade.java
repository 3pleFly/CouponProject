package com.coupon.demo.facade;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.service.CompanyService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyFacade extends ClientFacade{

    private CompanyService companyService;
    private boolean isLoggedIn = false;
    @ToString.Exclude
    private Company company;

    @Autowired
    public CompanyFacade(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public boolean login(String email, String password) {
        companyService.isCompanyExists(email, password).ifPresent((loggedCompany -> {
            company = loggedCompany;
            isLoggedIn = true;
        }));
        throw new LoginFailed("Admin login failed!");
    }


    public void addCoupon(Coupon coupon) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Company is not logged in");
        }
        companyService.addCoupon(coupon);
        company = getCompanyDetails();
    }

    public Coupon updateCoupon(Coupon coupon) {
        if (isLoggedIn == false) {
            throw new LoginFailed("Company is not logged in");
        }
        return companyService.updateCoupon(coupon);
    }

    @Transactional
    public void deleteCoupon(Coupon coupon) {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        companyService.deleteCoupon(coupon);
    }

    public List<Coupon> getCompanyCoupons() {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return companyService.getCompanyCoupons(company);
    }

    public List<Coupon> getCompanyCoupons(Category category) {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return companyService.getCompanyCoupons(category, company);
    }

    public List<Coupon> getCompanyCoupons(double maxPrice) {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return companyService.getCompanyCoupons(maxPrice, company);
    }

    public Company getCompanyDetails() {
        if (!isLoggedIn) {
            throw new LoginFailed("Company is not logged in");
        }
        return companyService.getCompanyDetails(company.getId());
    }
}
