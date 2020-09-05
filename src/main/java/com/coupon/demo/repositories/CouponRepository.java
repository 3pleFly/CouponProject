package com.coupon.demo.repositories;

import com.coupon.demo.beans.Category;
import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Coupon;
import com.coupon.demo.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

    @Modifying
    @Query(value = "delete from customers_vs_coupons where coupons_id in (select coupons_id " +
            "from" +
            " coupons where company_id = ?1)", nativeQuery = true)
    void deleteFromCvCByCompanyId(Long id);

    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons WHERE coupons_id = ?1", nativeQuery = true)
    void deleteFromCvCByCouponId(Long id);

    List<Coupon> findAllByCompany(Company company);

    List<Coupon> findAllByCompanyAndCategory(Company company, Category category);

    @Query("SELECT c from Coupon c WHERE c.company = ?1 and c.price < ?2")
    List<Coupon> findAllByCompanyAndMaxPrice(Company company, double maxPrice);

    List<Coupon> findAllByCustomerAndCategory(Customer customer, Category category);

    @Query("SELECT c from Coupon c WHERE c.customer = ?1 and c.price < ?2")
    List<Coupon> findAllByCustomerAndMaxPrice(Customer customer, double maxPrice);


}