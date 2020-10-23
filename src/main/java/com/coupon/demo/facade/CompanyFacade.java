package com.coupon.demo.facade;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.service.dao.CompanyDao;
import com.coupon.demo.service.dao.CouponDao;
import com.coupon.demo.service.dao.CustomerDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class CompanyFacade {

    private final CompanyDao companyDao;
    private final CouponDao couponDao;

    public void addCoupon(Coupon coupon) {
        couponDao.addCoupon(coupon);
    }

    public Coupon updateCoupon(Coupon coupon) {
        return couponDao.updateCoupon(coupon);
    }

    @Transactional
    public void deleteCoupon(Coupon coupon) {
        couponDao.deleteCoupon(coupon);
    }

    public List<Coupon> getCompanyCoupons(Long companyId) {
        return couponDao.getCompanyCoupons(companyId);
    }

    public List<Coupon> getCompanyCoupons(Category category, Long companyId) {
        return couponDao.getCompanyCoupons(category, companyId);
    }

    public List<Coupon> getCompanyCoupons(double maxPrice, Long companyId) {
        return couponDao.getCompanyCoupons(maxPrice, companyId);
    }

    public Company getCompanyDetails(Long companyId) {
        return companyDao.getOneCompany(companyId);
    }


}
