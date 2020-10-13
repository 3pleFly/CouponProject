package com.coupon.demo.facade;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.exception.CouponExpired;
import com.coupon.demo.exception.CouponNotAvailable;
import com.coupon.demo.service.dao.CompanyDao;
import com.coupon.demo.service.dao.CouponDao;
import com.coupon.demo.service.dao.CustomerDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class CustomerFacade {

    private CompanyDao companyDao;
    private CouponDao couponDao;
    private CustomerDao customerDao;


    public void purchaseCoupon(Coupon coupon, Long customerID) {
        if (coupon.getAmount().equals(0)) {
            throw new CouponNotAvailable("The coupon's available amount is 0");
        }
        if (coupon.getEndDate().isBefore(LocalDate.now())) {
            throw new CouponExpired("The coupon is expired");
        }
        if (customerDao.getOneCustomer(customerID).getCoupons().contains(coupon)) {
            throw new RuntimeException("Customer already purchased this coupon");
        }
        couponDao.addCouponPurchase(coupon, customerID);
    }

    public List<Coupon> getCustomerCoupons(Long customerID) {
        return couponDao.getCustomerCoupons(customerID);
    }

    public List<Coupon> getCustomerCoupons(Category category, Long customerID) {
        return couponDao.getCustomerCoupons(category, customerID);
    }

    public List<Coupon> getCustomerCoupons(double maxPrice, Long customerID) {
        return couponDao.getCustomerCoupons(maxPrice, customerID);
    }

    public Customer getCustomerDetails(Long customerID) {
        return customerDao.getOneCustomer(customerID);
    }

}
