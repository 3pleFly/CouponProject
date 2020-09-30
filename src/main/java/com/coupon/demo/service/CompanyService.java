package com.coupon.demo.service;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@ToString
@Service
public class CompanyService {

    private CategoryRepository categoryRepository;
    private CompanyRepository companyRepository;
    private CouponRepository couponRepository;
    private CustomerRepository customerRepository;


    @Autowired
    public CompanyService(CategoryRepository categoryRepository,
                          CompanyRepository companyRepository, CouponRepository couponRepository,
                          CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
    }

    public Optional<Company> isCompanyExists(String email, String password) {
        return Optional.of(companyRepository.findByEmailAndPassword(email,password));
    }

    public void addCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }

    public Coupon updateCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public void deleteCoupon(Coupon coupon) {
        couponRepository.deleteFromCvCByCouponId(coupon.getId());
        couponRepository.delete(coupon);
    }

    public List<Coupon> getCompanyCoupons(Company company) {
        return couponRepository.findAllByCompany(company);
    }

    public List<Coupon> getCompanyCoupons(Category category, Company company) {
        return couponRepository.findAllByCompanyAndCategory(company, category);
    }

    public List<Coupon> getCompanyCoupons(double maxPrice, Company company) {
        return couponRepository.findAllByCompanyAndMaxPrice(company, maxPrice);
    }

    public Company getCompanyDetails(Long companyId) {
        return companyRepository.findById(companyId).get();
    }
}
