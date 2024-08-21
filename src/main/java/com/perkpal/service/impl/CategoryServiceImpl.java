package com.perkpal.service.impl;

import com.perkpal.dto.CategoryDto;
import com.perkpal.dto.CategoryActivityDto;
import com.perkpal.entity.Category;
import com.perkpal.entity.Activity;
import com.perkpal.repository.CategoryRepository;
import com.perkpal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        Category newCategory = categoryRepository.save(category);
        return mapToDto(newCategory);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setCategoryName(categoryDto.getCategoryName());
        category.setActivities(categoryDto.getActivities().stream()
                .map(this::mapToActivityEntity)  // Updated method reference
                .collect(Collectors.toSet()));

        Category updatedCategory = categoryRepository.save(category);
        return mapToDto(updatedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    // Mapping methods
    private CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setActivities(category.getActivities().stream()
                .map(this::mapToCategoryActivityDto)
                .collect(Collectors.toSet()));
        return categoryDto;
    }

    private Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setActivities(categoryDto.getActivities().stream()
                .map(this::mapToActivityEntity)
                .collect(Collectors.toSet()));
        return category;
    }

    // New methods for Activity and CategoryActivityDto mapping
    private Activity mapToActivityEntity(CategoryActivityDto activityDto) {
        Activity activity = new Activity();
        activity.setId(activityDto.getId());
        activity.setActivityName(activityDto.getActivityName());
        // Map other fields accordingly
        return activity;
    }

    private CategoryActivityDto mapToCategoryActivityDto(Activity activity) {
        CategoryActivityDto activityDto = new CategoryActivityDto();
        activityDto.setId(activity.getId());
        activityDto.setActivityName(activity.getActivityName());
        // Map other fields accordingly
        return activityDto;
    }
}
