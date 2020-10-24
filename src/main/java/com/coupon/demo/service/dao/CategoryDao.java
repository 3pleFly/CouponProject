package com.coupon.demo.service.dao;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CouponRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CategoryDao {

    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCoupon(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.deleteById(category.getId());
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getOneCategory(Long id) {
        return categoryRepository.findById(id).get();
    }

}
