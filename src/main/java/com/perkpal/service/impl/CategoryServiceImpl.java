package com.perkpal.service.impl;

import com.perkpal.dto.CategoryDto;
import com.perkpal.dto.CategoryActivityDto;
import com.perkpal.dto.CategoryForActivityFilterDto;
import com.perkpal.dto.ClubDto;
import com.perkpal.entity.Category;
import com.perkpal.entity.Activity;
import com.perkpal.repository.CategoryRepository;
import com.perkpal.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;


    /**
     * Creates a new category.
     *
     * @param categoryDto The data transfer object containing the category details to be created.
     * @return The created CategoryDto containing the saved category details.
     * @throws IllegalArgumentException if the categoryDto is null.
     */
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        Category newCategory = categoryRepository.save(category);
        return mapToDto(newCategory);
    }


    /**
     * Updates an existing category by ID.
     *
     * @param id          The ID of the category to be updated.
     * @param categoryDto The data transfer object containing the updated category details.
     * @return The updated CategoryDto containing the updated category details.
     * @throws RuntimeException if the category with the given ID is not found.
     */
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


    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to be retrieved.
     * @return The CategoryDto containing the retrieved category details.
     * @throws RuntimeException if the category with the given ID is not found.
     */
    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapToDto(category);
    }


    /**
     * Retrieves all categories.
     *
     * @return A list of CategoryDto objects containing the details of all categories.
     */
    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::mapToDto).collect(Collectors.toList());
    }


    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to be deleted.
     * @throws RuntimeException if the category with the given ID is not found.
     */
    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }


    /**
     * Retrieves all categories formatted for activity filtering.
     *
     * @return A list of CategoryForActivityFilterDto objects containing category details for filtering activities.
     */
    @Override
    public List<CategoryForActivityFilterDto> getAllCategoriesForActivityFilter() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> mapper.map(category, CategoryForActivityFilterDto.class)).collect(Collectors.toList());
    }


    /**
     * Converts a Category entity to a CategoryDto.
     *
     * @param category The Category entity to be converted.
     * @return The corresponding CategoryDto containing the category details.
     */
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


    /**
     * Converts a CategoryDto to a Category entity.
     *
     * @param categoryDto The CategoryDto to be converted.
     * @return The corresponding Category entity containing the category details.
     */
    private Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setActivities(categoryDto.getActivities().stream()
                .map(this::mapToActivityEntity)
                .collect(Collectors.toSet()));
        return category;
    }


    /**
     * Converts a CategoryActivityDto to an Activity entity.
     *
     * @param activityDto The CategoryActivityDto to be converted.
     * @return The corresponding Activity entity containing the activity details.
     */
    // New methods for Activity and CategoryActivityDto mapping
    private Activity mapToActivityEntity(CategoryActivityDto activityDto) {
        Activity activity = new Activity();
        activity.setId(activityDto.getId());
        activity.setActivityName(activityDto.getActivityName());
        // Map other fields accordingly
        return activity;
    }


    /**
     * Converts an Activity entity to a CategoryActivityDto.
     *
     * @param activity The Activity entity to be converted.
     * @return The corresponding CategoryActivityDto containing the activity details.
     */
    private CategoryActivityDto mapToCategoryActivityDto(Activity activity) {
        CategoryActivityDto activityDto = new CategoryActivityDto();
        activityDto.setId(activity.getId());
        activityDto.setActivityName(activity.getActivityName());
        // Map other fields accordingly
        return activityDto;
    }
}
