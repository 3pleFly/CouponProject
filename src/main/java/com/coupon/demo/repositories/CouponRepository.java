package com.coupon.demo.repositories;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Modifying
    @Query(value = "delete cvc from customers_vs_coupons cvc join coupons c on cvc.coupons_id=c.id" +
            " where c.company_id=?1", nativeQuery = true)
    void deleteFromCvCByCompanyId(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from customers_vs_coupons where coupons_id = ?1", nativeQuery = true)
    void deleteFromCvCByCouponId(Long id);

    void deleteByCompany(Company company);

    List<Coupon> findAllByCompany(Company company);

    List<Coupon> findAllByCompanyAndCategory(Company company, Category category);

    @Query("select c from Coupon c where c.company = ?1 and c.price < ?2")
    List<Coupon> findAllByCompanyAndMaxPrice(Company company, double maxPrice);

    List<Coupon> findAllByCustomerAndCategory(Customer customer, Category category);

    @Query("select c from Coupon c join c.customer customer  where customer.id = ?1 and c.price <" +
            " ?2")
    List<Coupon> findAllByCustomerAndMaxPrice(Long id, double maxPrice);


}