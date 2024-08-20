package com.perkpal.service;

import com.perkpal.dto.ActivityDto;
import com.perkpal.dto.ActivityPostDto;

import java.util.List;

public interface ActivityService {
    List<ActivityDto> getActivity();
    ActivityPostDto createActivity(ActivityPostDto activityPostDto);
}
