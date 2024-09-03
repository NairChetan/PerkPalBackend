package com.perkpal.service;

import com.perkpal.dto.CategoryActivityDto;
import com.perkpal.dto.CategoryDto;
import com.perkpal.entity.Category;
import com.perkpal.repository.CategoryRepository;
import com.perkpal.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryDto categoryDto;
    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

         // You might need to map from DTO to Entity
    }

    @Test
    public void testCreateCategory() {
        // Initialize CategoryActivityDto
        CategoryActivityDto activityDto = new CategoryActivityDto();
        activityDto.setId(1L);
        activityDto.setActivityName("Sample Activity");
        activityDto.setWeightagePerHour(10);

        Set<CategoryActivityDto> activitiesDto = new HashSet<>();
        activitiesDto.add(activityDto);

        // Initialize CategoryDto with activities
        categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setCategoryName("Test Category");
        categoryDto.setActivities(activitiesDto);

        // Initialize Category entity with activities (mapping might vary based on your actual entity design)
        category = new Category();
        category.setId(1L);
        category.setCategoryName("Test Category");
        category.setActivities(new HashSet<>());
        // Mock the save method of the repository to return the Category object
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Call the service method
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);

        // Assertions
        assertEquals(categoryDto.getCategoryName(), createdCategory.getCategoryName());
        assertEquals(categoryDto.getId(), createdCategory.getId());
        assertEquals(categoryDto.getActivities().size(), createdCategory.getActivities().size());

        // Additional assertions to ensure activity details are correctly mapped (if applicable)
        CategoryActivityDto createdActivity = createdCategory.getActivities().iterator().next();
        CategoryActivityDto originalActivity = categoryDto.getActivities().iterator().next();

        assertEquals(originalActivity.getActivityName(), createdActivity.getActivityName());
        assertEquals(originalActivity.getWeightagePerHour(), createdActivity.getWeightagePerHour());
    }
}
