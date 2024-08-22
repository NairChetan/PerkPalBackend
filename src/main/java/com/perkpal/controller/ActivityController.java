package com.perkpal.controller;

import com.perkpal.dto.ActivityCateogryPostDto;
import com.perkpal.dto.ActivityPostDto;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.perkpal.constants.Message.*;

@RestController
@RequestMapping("/api/v1/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public ResponseEntity<Object> getActivities() {
        return ResponseHandler.responseBuilder(REQUESTED_ACTIVITY_DETAILS, HttpStatus.OK, activityService.getActivity());
    }

    @PostMapping
    public ResponseEntity<Object> createActivity(@RequestBody ActivityPostDto activityPostDto) {
        ActivityPostDto createdActivity = activityService.createActivity(activityPostDto);
        return ResponseHandler.responseBuilder(ACTIVITY_DETAILS, HttpStatus.CREATED, createdActivity);
    }

    @PostMapping("/create-with-category")
    public ResponseEntity<Object> createActivityWithCategory(@RequestBody ActivityCateogryPostDto activityCateogryPostDto) {
        ActivityCateogryPostDto newActivityCategoryPostDto = activityService.createActivityWithCategory(activityCateogryPostDto);
        return ResponseHandler.responseBuilder(ACTIVITY_DETAILS_CATEGORY, HttpStatus.CREATED, newActivityCategoryPostDto);
    }
}
