package com.coupon.demo.service.dao;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.exception.AlreadyExists;
import com.coupon.demo.exception.BadUpdate;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CouponDao {

    private CouponRepository couponRepository;
    private CustomerRepository customerRepository;
    private CompanyRepository companyRepository;

    public Coupon addCoupon(Coupon coupon) {
        couponRepository.findAllByTitle(coupon.getTitle()).forEach(returnedCoupons -> {
            if (coupon.getCompany().equals(returnedCoupons.getCompany())) {
                throw new AlreadyExists("Coupon already exists for this company, and by this " +
                        "title: " + coupon.getTitle());
            }
        });
        try {
            return couponRepository.save(coupon);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Coupon updateCoupon(Coupon coupon) {
        if (!couponRepository.findById(coupon.getId()).get().getCompany()
                .equals(coupon.getCompany())) {
            throw new BadUpdate("Cannot change coupon's company: " + coupon.getCompany());
        }
        try {
            return couponRepository.save(coupon);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteCoupon(Coupon coupon) {
        try {
            couponRepository.deleteFromCvCByCouponId(coupon.getId());
            couponRepository.deleteById(coupon.getId());
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Coupon> getAllCoupons() {
        try {
            return couponRepository.findAll();
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Coupon getOneCoupon(Long id) {
        try {
            return couponRepository.findById(id).get();
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Coupon> getCompanyCoupons(Long companyID) {
        try {
            return companyRepository.findById(companyID).get().getCoupons();
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Coupon> getCompanyCoupons(Category category, Long companyID) {
        try {
            return couponRepository.findAllByCompanyAndCategory(
                    companyRepository.findById(companyID).get(), category);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Coupon> getCompanyCoupons(double maxPrice, Long companyID) {
        try {
            return couponRepository.findAllByCompanyAndMaxPrice(
                    companyRepository.findById(companyID).get(), maxPrice);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Coupon> getCustomerCoupons(Long customerID) {
        try {
            return customerRepository.findById(customerID).get().getCoupons();
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Coupon> getCustomerCoupons(Category category, Long customerID) {
        try {
            return couponRepository.findAllByCustomerAndCategory(
                    customerRepository.findById(customerID).get(), category);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Coupon> getCustomerCoupons(double maxPrice, Long customerID) {
        try {
            return couponRepository.findAllByCustomerAndMaxPrice(customerID, maxPrice);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addCouponPurchase(Coupon coupon, Long customerID) {
        try {
            Customer customer = customerRepository
                    .findById(customerID).get();
            customer.getCoupons().add(coupon);
            coupon.setAmount(coupon.getAmount() - 1);
            customerRepository.save(customer);
            couponRepository.save(coupon);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

//    public void deleteCouponPurchase(Long customerID, Long couponID) {
//
//    }
}
