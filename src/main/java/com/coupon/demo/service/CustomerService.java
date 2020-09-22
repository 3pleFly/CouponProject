package com.coupon.demo.service;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.Coupon;
import com.coupon.demo.beans.Customer;
import com.coupon.demo.exception.CouponExpired;
import com.coupon.demo.exception.CouponNotAvailable;
import com.coupon.demo.exception.LoginFailed;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@ToString
@Service
public class CustomerService {

    private CategoryRepository categoryRepository;
    private CompanyRepository companyRepository;
    private CustomerRepository customerRepository;
    private CouponRepository couponRepository;

    @Autowired
    public CustomerService(CategoryRepository categoryRepository,
                           CompanyRepository companyRepository, CouponRepository couponRepository,
                           CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> isCustomerExists(String email, String password) {
        return Optional.of(customerRepository.findByEmailAndPassword(email,password));
    }

    public void purchaseCoupon(Coupon coupon) {
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepository.save(coupon);
    }

    public List<Coupon> getCustomerCoupons(Customer customer) {
        return customerRepository.findById(customer.getId()).get().getCoupons();
    }

    public List<Coupon> getCustomerCoupons(Category category, Customer customer) {
        return couponRepository.findAllByCustomerAndCategory(customer, category);
    }

    public List<Coupon> getCustomerCoupons(double maxPrice, Customer customer) {
        return couponRepository.findAllByCustomerAndMaxPrice(customer.getId(), maxPrice);
    }

    public Customer getCustomerDetails(Long customerId) {
        return customerRepository.findById(customerId).get();
    }
}

