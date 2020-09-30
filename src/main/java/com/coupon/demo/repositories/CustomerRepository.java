package com.coupon.demo.repositories;

import com.coupon.demo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    boolean existsByEmailAndPassword(String email, String password);

    Customer findByEmailAndPassword(String email, String password);

    Optional<Customer> findByEmail(String email);
}
