package com.coupon.demo.repositories;

import com.coupon.demo.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsByCategory(String category);

    Optional<Category> findByCategory(String category);

}
