package net.javaguides.lms.service;

import net.javaguides.lms.entity.Category;
import net.javaguides.lms.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category already exists with name: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Integer id, Category categoryDetails) {
        Category category = getCategoryById(id);
        category.setName(categoryDetails.getName());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}