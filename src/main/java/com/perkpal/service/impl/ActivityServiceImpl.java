package com.perkpal.service.impl;

import com.perkpal.dto.ActivityDto;
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
    @Override
    public List<ActivityDto> getActivity() {
            List<Activity> activityList = activityRepository.findAll();
            return activityList.stream().map(activity -> mapper.map(activity, ActivityDto.class)).collect(Collectors.toList());
    }

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

}
