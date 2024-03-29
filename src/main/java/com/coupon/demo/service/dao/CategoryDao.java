package com.coupon.demo.service.dao;

import com.coupon.demo.model.entities.Category;
import com.coupon.demo.exception.AlreadyExists;
import com.coupon.demo.exception.MissingAttributes;
import com.coupon.demo.exception.NotFound;
import com.coupon.demo.repositories.CategoryRepository;
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
        checkNull(category);
        if (categoryRepository.existsByCategory(category.getCategory())){
            throw new AlreadyExists("Category already exists: " + category.getCategory());
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        checkNull(category);
        categoryRepository.findByCategory(category.getCategory()).ifPresent(category1 -> {
            if (!category1.getId().equals(category.getId())) {
                throw new AlreadyExists("Category already exists: " + category.getCategory());
            }
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

}
