package com.perkpal.service;

import com.perkpal.dto.ActivityCateogryPostDto;
import com.perkpal.dto.ActivityDto;
import com.perkpal.dto.ActivityGetBasedOnCategoryDto;
import com.perkpal.dto.ActivityPostDto;
import com.perkpal.entity.Activity;
import com.perkpal.entity.Category;
import com.perkpal.exception.ResourceNotFoundException;
import com.perkpal.repository.ActivityRepository;
import com.perkpal.repository.CategoryRepository;
import com.perkpal.service.impl.ActivityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityServiceImplTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ActivityServiceImpl activityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenActivities_whenGetActivity_thenReturnActivityDtoList() {
        // Given
        Activity activity1 = new Activity();
        activity1.setActivityName("Activity1");
        activity1.setWeightagePerHour(10);
        Category category1 = new Category();
        category1.setCategoryName("Category1");
        category1.setDescription("Description1");
        activity1.setCategoryId(category1);

        Activity activity2 = new Activity();
        activity2.setActivityName("Activity2");
        activity2.setWeightagePerHour(20);
        Category category2 = new Category();
        category2.setCategoryName("Category2");
        category2.setDescription("Description2");
        activity2.setCategoryId(category2);

        List<Activity> activityList = new ArrayList<>();
        activityList.add(activity1);
        activityList.add(activity2);

        ActivityDto dto1 = new ActivityDto();
        dto1.setActivityName("Activity1");
        dto1.setWeightagePerHour(10);
        dto1.setCategoryName("Category1");
        dto1.setCategoryDescription("Description1");

        ActivityDto dto2 = new ActivityDto();
        dto2.setActivityName("Activity2");
        dto2.setWeightagePerHour(20);
        dto2.setCategoryName("Category2");
        dto2.setCategoryDescription("Description2");

        List<ActivityDto> dtoList = new ArrayList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);

        // Mock repository and mapper
        when(activityRepository.findAll()).thenReturn(activityList);
        when(mapper.map(any(Activity.class), any(Class.class))).thenAnswer(invocation -> {
            Activity activity = invocation.getArgument(0);
            return activity1.getActivityName().equals(activity.getActivityName()) ? dto1 : dto2;
        });

        // When
        List<ActivityDto> result = activityService.getActivity();

        // Then
        assertEquals(dtoList, result);
    }
    @Test
    void givenValidActivityPostDto_whenCreateActivity_thenReturnActivityPostDto() {
        // Given
        ActivityPostDto activityPostDto = new ActivityPostDto();
        activityPostDto.setActivityName("New Activity");
        activityPostDto.setCategoryId(1L);
        activityPostDto.setWeightagePerHour(15);
        activityPostDto.setDescription("Description of new activity");
        activityPostDto.setCreatedBy(100L);

        Category category = new Category();
        category.setId(1L);

        Activity activity = new Activity();
        activity.setActivityName(activityPostDto.getActivityName());
        activity.setCategoryId(category);
        activity.setWeightagePerHour(activityPostDto.getWeightagePerHour());
        activity.setDescription(activityPostDto.getDescription());
        activity.setCreatedBy(activityPostDto.getCreatedBy());

        Activity savedActivity = new Activity();
        savedActivity.setId(1L); // Set an ID to simulate a saved entity
        savedActivity.setActivityName(activityPostDto.getActivityName());
        savedActivity.setCategoryId(category);
        savedActivity.setWeightagePerHour(activityPostDto.getWeightagePerHour());
        savedActivity.setDescription(activityPostDto.getDescription());
        savedActivity.setCreatedBy(activityPostDto.getCreatedBy());

        ActivityPostDto savedActivityPostDto = new ActivityPostDto();
        savedActivityPostDto.setActivityName(savedActivity.getActivityName());
        savedActivityPostDto.setCategoryId(savedActivity.getCategoryId().getId());
        savedActivityPostDto.setWeightagePerHour(savedActivity.getWeightagePerHour());
        savedActivityPostDto.setDescription(savedActivity.getDescription());
        savedActivityPostDto.setCreatedBy(savedActivity.getCreatedBy());

        // Mock repository and mapper
        when(categoryRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(category));
        when(activityRepository.save(any(Activity.class))).thenReturn(savedActivity);
        when(mapper.map(any(Activity.class), eq(ActivityPostDto.class))).thenReturn(savedActivityPostDto);

        // When
        ActivityPostDto result = activityService.createActivity(activityPostDto);

        // Then
        assertEquals(savedActivityPostDto, result);
    }

    @Test
    void givenInvalidCategoryId_whenCreateActivity_thenThrowResourceNotFoundException() {
        // Given
        ActivityPostDto activityPostDto = new ActivityPostDto();
        activityPostDto.setActivityName("New Activity");
        activityPostDto.setCategoryId(1L);
        activityPostDto.setWeightagePerHour(15);
        activityPostDto.setDescription("Description of new activity");
        activityPostDto.setCreatedBy(100L);

        // Mock repository to return an empty Optional
        when(categoryRepository.findById(eq(1L))).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> activityService.createActivity(activityPostDto));
    }
    @Test
    void givenValidActivityCateogryPostDto_whenCreateActivityWithCategory_thenReturnActivityCateogryPostDto() {
        // Given
        ActivityCateogryPostDto activityCateogryPostDto = new ActivityCateogryPostDto();
        activityCateogryPostDto.setActivityName("New Activity");
        activityCateogryPostDto.setCategoryName("New Category");
        activityCateogryPostDto.setWeightagePerHour(15);
        activityCateogryPostDto.setDescription("Description of new activity");
        activityCateogryPostDto.setCreatedBy(100L);

        Category newCategory = new Category();
        newCategory.setId(1L);
        newCategory.setCategoryName(activityCateogryPostDto.getCategoryName());
        newCategory.setCreatedBy(activityCateogryPostDto.getCreatedBy());

        Activity activity = new Activity();
        activity.setActivityName(activityCateogryPostDto.getActivityName());
        activity.setCategoryId(newCategory);
        activity.setWeightagePerHour(activityCateogryPostDto.getWeightagePerHour());
        activity.setDescription(activityCateogryPostDto.getDescription());
        activity.setCreatedBy(activityCateogryPostDto.getCreatedBy());

        Activity savedActivity = new Activity();
        savedActivity.setId(1L); // Simulate a saved entity with ID
        savedActivity.setActivityName(activity.getActivityName());
        savedActivity.setCategoryId(newCategory);
        savedActivity.setWeightagePerHour(activity.getWeightagePerHour());
        savedActivity.setDescription(activity.getDescription());
        savedActivity.setCreatedBy(activity.getCreatedBy());

        ActivityCateogryPostDto savedActivityCateogryPostDto = new ActivityCateogryPostDto();
        savedActivityCateogryPostDto.setActivityName(savedActivity.getActivityName());
        savedActivityCateogryPostDto.setCategoryName(savedActivity.getCategoryId().getCategoryName());
        savedActivityCateogryPostDto.setWeightagePerHour(savedActivity.getWeightagePerHour());
        savedActivityCateogryPostDto.setDescription(savedActivity.getDescription());
        savedActivityCateogryPostDto.setCreatedBy(savedActivity.getCreatedBy());
        savedActivityCateogryPostDto.setCategoryId(savedActivity.getCategoryId().getId());

        // Mock repository and mapper
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);
        when(activityRepository.save(any(Activity.class))).thenReturn(savedActivity);
        when(mapper.map(any(ActivityCateogryPostDto.class), eq(Activity.class))).thenAnswer(invocation -> {
            ActivityCateogryPostDto dto = invocation.getArgument(0);
            Activity act = new Activity();
            act.setActivityName(dto.getActivityName());
            act.setWeightagePerHour(dto.getWeightagePerHour());
            act.setDescription(dto.getDescription());
            act.setCreatedBy(dto.getCreatedBy());
            return act;
        });
        when(mapper.map(any(Activity.class), eq(ActivityCateogryPostDto.class))).thenReturn(savedActivityCateogryPostDto);

        // When
        ActivityCateogryPostDto result = activityService.createActivityWithCategory(activityCateogryPostDto);

        // Then
        assertEquals(savedActivityCateogryPostDto, result);
    }

    @Test
    void givenMissingCategoryName_whenCreateActivityWithCategory_thenThrowIllegalArgumentException() {
        // Given
        ActivityCateogryPostDto activityCateogryPostDto = new ActivityCateogryPostDto();
        activityCateogryPostDto.setActivityName("New Activity");
        activityCateogryPostDto.setCategoryName(""); // Empty category name
        activityCateogryPostDto.setWeightagePerHour(15);
        activityCateogryPostDto.setDescription("Description of new activity");
        activityCateogryPostDto.setCreatedBy(100L);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> activityService.createActivityWithCategory(activityCateogryPostDto));
    }

    @Test
    void givenActivities_whenGetActivitiesByCategoryName_thenReturnActivityGetBasedOnCategoryDtoList() {
        // Given
        String categoryName = "Category1";

        Category category = new Category();
        category.setCategoryName(categoryName);

        Activity activity1 = new Activity();
        activity1.setId(1L);
        activity1.setActivityName("Activity1");
        activity1.setCategoryId(category);

        Activity activity2 = new Activity();
        activity2.setId(2L);
        activity2.setActivityName("Activity2");
        activity2.setCategoryId(category);

        List<Activity> activityList = new ArrayList<>();
        activityList.add(activity1);
        activityList.add(activity2);

        ActivityGetBasedOnCategoryDto dto1 = new ActivityGetBasedOnCategoryDto();
        dto1.setId(activity1.getId());
        dto1.setActivityName(activity1.getActivityName());
        dto1.setCategoryName(activity1.getCategoryId().getCategoryName());

        ActivityGetBasedOnCategoryDto dto2 = new ActivityGetBasedOnCategoryDto();
        dto2.setId(activity2.getId());
        dto2.setActivityName(activity2.getActivityName());
        dto2.setCategoryName(activity2.getCategoryId().getCategoryName());

        List<ActivityGetBasedOnCategoryDto> dtoList = new ArrayList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);

        // Mock repository and mapper
        when(activityRepository.findByCategoryIdCategoryName(anyString())).thenReturn(activityList);
        when(mapper.map(activity1, ActivityGetBasedOnCategoryDto.class)).thenReturn(dto1);
        when(mapper.map(activity2, ActivityGetBasedOnCategoryDto.class)).thenReturn(dto2);

        // When
        List<ActivityGetBasedOnCategoryDto> result = activityService.getActivitiesByCategoryName(categoryName);

        // Then
        assertEquals(dtoList, result);
    }

    @Test
    void givenNoActivities_whenGetActivitiesByCategoryName_thenReturnEmptyList() {
        // Given
        String categoryName = "NonExistentCategory";

        // Mock repository to return an empty list
        when(activityRepository.findByCategoryIdCategoryName(anyString())).thenReturn(new ArrayList<>());

        // When
        List<ActivityGetBasedOnCategoryDto> result = activityService.getActivitiesByCategoryName(categoryName);

        // Then
        assertEquals(new ArrayList<>(), result);
    }
}


