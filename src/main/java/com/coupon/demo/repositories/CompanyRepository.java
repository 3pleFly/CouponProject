package com.coupon.demo.repositories;

import com.coupon.demo.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByNameOrEmail(String name, String email);

    Company findByEmailAndPassword(String email, String password);

    Optional<Company> findByEmail(String email);

    Optional<List<Company>> findAllByEmail(String email);

    Optional<Company> findByName(String name);

    Optional<List<Company>> findAllByName(String name);

}
