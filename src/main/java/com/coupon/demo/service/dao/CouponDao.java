package com.coupon.demo.service.dao;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CouponDao {

    private CouponRepository couponRepository;
    private CustomerRepository customerRepository;
    private CompanyRepository companyRepository;

    public Coupon addCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon updateCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public void deleteCoupon(Coupon coupon) {
        couponRepository.deleteFromCvCByCouponId(coupon.getId());
        couponRepository.deleteById(coupon.getId());
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon getOneCoupon(Long id) {
        return couponRepository.findById(id).get();
    }

    public List<Coupon> getCompanyCoupons(Long companyID) {
//        return couponRepository.findAllByCompany(companyRepository.findById(companyID).get());
        return companyRepository.findById(companyID).get().getCoupons();
    }

    public List<Coupon> getCompanyCoupons(Category category, Long companyID) {
        return couponRepository.findAllByCompanyAndCategory(
                companyRepository.findById(companyID).get(), category);
    }

    public List<Coupon> getCompanyCoupons(double maxPrice, Long companyID) {
        return couponRepository.findAllByCompanyAndMaxPrice(
                companyRepository.findById(companyID).get(), maxPrice);
    }

    public List<Coupon> getCustomerCoupons(Long customerID) {
        return customerRepository.findById(customerID).get().getCoupons();
    }

    public List<Coupon> getCustomerCoupons(Category category, Long customerID) {
        return couponRepository.findAllByCustomerAndCategory(
                customerRepository.findById(customerID).get(), category);
    }

    public List<Coupon> getCustomerCoupons(double maxPrice, Long customerID) {
        return couponRepository.findAllByCustomerAndMaxPrice(customerID, maxPrice);
    }

    public void addCouponPurchase(Coupon coupon, Long customerID) {
        Customer customer = customerRepository
                .findById(customerID).get();
        customer.getCoupons().add(coupon);
        coupon.setAmount(coupon.getAmount() - 1);
        customerRepository.save(customer);
        couponRepository.save(coupon);
    }

    public void deleteCouponPurchase(Long customerID, Long couponID) {

    }
}
