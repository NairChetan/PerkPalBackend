package com.perkpal.controller;

import com.perkpal.dto.ActivityCateogryPostDto;
import com.perkpal.dto.ActivityDto;
import com.perkpal.dto.ActivityPostDto;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.perkpal.constants.Message.REQUESTED_ACTIVITY_DETAILS;

@RestController
@RequestMapping("/api/v1/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @GetMapping
    public ResponseEntity<Object> getActivities(){
        return ResponseHandler.responseBuilder(REQUESTED_ACTIVITY_DETAILS, HttpStatus.OK,activityService.getActivity());
    }
    @PostMapping
    public ResponseEntity<ActivityPostDto> createActivity(@RequestBody ActivityPostDto activityPostDto){
        return new ResponseEntity<>(activityService.createActivity(activityPostDto), HttpStatus.CREATED);
    }
    @PostMapping("/create-with-category")
    public ResponseEntity<ActivityCateogryPostDto> createActivityWithCategory(@RequestBody ActivityCateogryPostDto activityCateogryPostDto) {
        ActivityCateogryPostDto newActivityCategoryPostDto = activityService.createActivityWithCategory(activityCateogryPostDto);
        return new ResponseEntity<>(newActivityCategoryPostDto, HttpStatus.CREATED);
    }


}
