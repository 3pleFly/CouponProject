package com.coupon.demo.repositories;

import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from customers_vs_coupons where coupons_id in ( select coupons_id " +
            "from" +
            " coupons where company_id = ?1 )", nativeQuery = true)
    void deleteFromCvC(Long id);
}