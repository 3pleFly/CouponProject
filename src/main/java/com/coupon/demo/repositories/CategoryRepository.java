package com.coupon.demo.repositories;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsByCategory(CategoryType category);

    Optional<List<Category>> findAllByCategory(CategoryType category);

}
