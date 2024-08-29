package com.perkpal.service.impl;

import com.perkpal.dto.ActivityCateogryPostDto;
import com.perkpal.dto.ActivityDto;
import com.perkpal.dto.ActivityGetBasedOnCategoryDto;
import com.perkpal.dto.ActivityPostDto;
import com.perkpal.entity.Activity;
import com.perkpal.entity.Category;
import com.perkpal.exception.ResourceNotFoundException;
import com.perkpal.repository.ActivityRepository;
import com.perkpal.repository.CategoryRepository;
import com.perkpal.service.ActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;

    /**
     * Retrieves a list of all activities from the database.
     * This method fetches all activities, maps them to their corresponding DTOs, and returns the list.
     *
     * @return A list of ActivityDto objects representing all activities in the database.
     */

    @Override
    public List<ActivityDto> getActivity() {
        List<Activity> activityList = activityRepository.findAll();
        return activityList.stream().map(activity -> mapper.map(activity, ActivityDto.class)).collect(Collectors.toList());
    }


    /**
     * Creates a new activity in the database.
     * This method maps the data from the provided ActivityPostDto to a new Activity entity,
     * sets the associated category, and saves the new activity to the database.
     *
     * @param activityPostDto An object containing the details of the activity to be created, including the associated category ID.
     * @return An ActivityPostDto object representing the newly created activity with the saved details.
     * @throws ResourceNotFoundException if the category associated with the given ID is not found.
     */
    @Override
    public ActivityPostDto createActivity(ActivityPostDto activityPostDto) {
        // Fetch the Category entity
        Category category = categoryRepository.findById(activityPostDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", activityPostDto.getCategoryId()));

        // Manually map fields from the DTO to the new Activity entity
        Activity activity = new Activity();
        activity.setActivityName(activityPostDto.getActivityName());
        activity.setCategoryId(category); // Set the Category entity
        activity.setWeightagePerHour(activityPostDto.getWeightagePerHour());
        activity.setDescription(activityPostDto.getDescription()); // Set description if needed
        activity.setCreatedBy(activityPostDto.getCreatedBy()); // Ensure the createdBy field is set

        // Clear the id to ensure it's treated as a new entity
        activity.setId(null);

        // Save the new Activity entity
        Activity newActivity = activityRepository.save(activity);

        // Map the saved Activity back to the DTO
        ActivityPostDto newActivityPostDto = mapper.map(newActivity, ActivityPostDto.class);
        return newActivityPostDto;
    }


    /**
     * Creates a new activity along with a new category in the database.
     * This method checks if a category name is provided in the input DTO; if so, it creates a new Category entity.
     * It then creates a new Activity entity, associates it with the newly created or provided category, and saves it.
     *
     * @param activityCateogryPostDto An object containing the details of the activity and the category. The category name must be provided if a new category is to be created.
     * @return An ActivityCateogryPostDto object representing the newly created activity with the saved details and associated category.
     * @throws IllegalArgumentException if the category name is not provided when attempting to create a new category.
     */
    @Override
    public ActivityCateogryPostDto createActivityWithCategory(ActivityCateogryPostDto activityCateogryPostDto) {
        Category category;

        // Check if categoryName is provided; if so, create a new Category
        if (activityCateogryPostDto.getCategoryName() != null && !activityCateogryPostDto.getCategoryName().isEmpty()) {
            category = new Category();
            category.setId(null);
            category.setCategoryName(activityCateogryPostDto.getCategoryName());
            category.setCreatedBy(activityCateogryPostDto.getCreatedBy());
            category = categoryRepository.save(category);  // Save the new Category
        } else {
            // Handle the scenario where the category should be pre-existing or error out
            throw new IllegalArgumentException("Category name must be provided to create a new category.");
        }

        // Create a new Activity instance
        Activity activity = new Activity();
        // Map basic fields using the mapper
        mapper.map(activityCateogryPostDto, activity);

        // Set the new Category entity
        activity.setCategoryId(category);  // Set the new Category

        // Ensure the createdBy field is set
        activity.setCreatedBy(activityCateogryPostDto.getCreatedBy());
        activity.setId(null);  // Ensure this is treated as a new entity

        // Save the new Activity entity
        Activity newActivity = activityRepository.save(activity);

        // Map the saved Activity back to the DTO
        return mapper.map(newActivity, ActivityCateogryPostDto.class);
    }


    /**
     * Retrieves a list of activities associated with a specific category name from the database.
     * This method queries activities by their category name, maps them to their corresponding DTOs,
     * and returns the list.
     *
     * @param categoryName The name of the category for which activities are to be retrieved.
     * @return A list of ActivityGetBasedOnCategoryDto objects representing all activities associated
     * with the specified category name.
     */
    @Override
    public List<ActivityGetBasedOnCategoryDto> getActivitiesByCategoryName(String categoryName) {
        List<Activity> activityList = activityRepository.findByCategoryIdCategoryName(categoryName);

        // Map Activity entities to ActivityDto using ModelMapper
        return activityList.stream()
                .map(activity -> mapper.map(activity, ActivityGetBasedOnCategoryDto.class))
                .collect(Collectors.toList());
    }


}
