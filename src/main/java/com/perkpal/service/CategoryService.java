package com.perkpal.service;

import com.perkpal.dto.CategoryDto;
import com.perkpal.dto.CategoryForActivityFilterDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategories();
    void deleteCategory(Long id);
    List<CategoryForActivityFilterDto> getAllCategoriesForActivityFilter();
}
