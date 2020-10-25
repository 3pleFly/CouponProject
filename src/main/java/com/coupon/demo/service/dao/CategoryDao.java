package com.coupon.demo.service.dao;

import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.CategoryType;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.exception.AlreadyExists;
import com.coupon.demo.exception.MissingAttributes;
import com.coupon.demo.exception.MissingCategoryType;
import com.coupon.demo.exception.NotFound;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CouponRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CategoryDao {

    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        checkNull(category);
        if (categoryRepository.existsByCategory(category.getCategory())) {
            throw new AlreadyExists("Category already exists: " + category.getCategory().name());
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        checkNull(category);
        categoryRepository.findAllByCategory(category.getCategory()).ifPresent(categoriesByCategory -> {
            categoriesByCategory.forEach(oneCategory -> {
                if (!oneCategory.getId().equals(category.getId())) {
                    throw new AlreadyExists("Category already exists: " + category.getCategory());
                }
            });
        });
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryID) {
        checkExists(categoryID);
        categoryRepository.deleteById(categoryID);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getOneCategory(Long categoryID) {
        checkExists(categoryID);
        return categoryRepository.findById(categoryID).get();
    }

    private void checkNull(Category category) {
        if (category.getCategory() == null) {
            throw new MissingAttributes("Category name cannot be null");
        }
    }

    private void checkExists(Long categoryID) {
        if (!categoryRepository.existsById(categoryID)) {
            throw new NotFound("Category by ID not found: " + categoryID);
        }
    }

    private void checkCategoryType(CategoryType category) {
        List<Enum> enumValues = Arrays.asList(CategoryType.values());
        if (!enumValues.contains(category)) {
            throw new MissingCategoryType("This category is not recognized in the database, " +
                    "please");
        }
    }
}
