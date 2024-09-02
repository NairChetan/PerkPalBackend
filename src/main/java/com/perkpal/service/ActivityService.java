package com.perkpal.service;

import com.perkpal.dto.*;

import java.util.List;

public interface ActivityService {
    List<ActivityDto> getActivity();
    ActivityPostDto createActivity(ActivityPostDto activityPostDto);
    ActivityCateogryPostDto createActivityWithCategory(ActivityCateogryPostDto activityCateogryPostDto);
    List<ActivityGetBasedOnCategoryDto> getActivitiesByCategoryName(String categoryName);
    void deleteActivity(Long id);
    ActivityUpdateForAdminDto updateActivity(Long id, ActivityUpdateForAdminDto activityUpdateForAdminDto);
}
