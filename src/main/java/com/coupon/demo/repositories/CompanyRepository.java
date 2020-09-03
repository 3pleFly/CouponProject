package com.coupon.demo.repositories;

import com.coupon.demo.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    boolean existsByEmailAndPassword(String email, String password);

    Company findByEmailAndPassword(String email, String password);

    boolean existsByNameOrEmail(String name, String email);
}
