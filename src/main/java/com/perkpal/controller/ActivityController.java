package com.perkpal.controller;

import com.perkpal.dto.ActivityCateogryPostDto;
import com.perkpal.dto.ActivityDto;
import com.perkpal.dto.ActivityGetBasedOnCategoryDto;
import com.perkpal.dto.ActivityPostDto;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.perkpal.constants.Message.*;

@RestController
@RequestMapping("/api/v1/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * Retrieves a list of all activities from the database.
     * This function is typically used to fetch all available activities for display purposes on the frontend.
     *
     * @return A ResponseEntity containing a success message, HTTP status, and the list of activities
     * retrieved from the database.
     */
    @GetMapping
    public ResponseEntity<Object> getActivities() {
        return ResponseHandler.responseBuilder(REQUESTED_ACTIVITY_DETAILS, HttpStatus.OK, activityService.getActivity());
    }


    /**
     * Creates a new activity in the database.
     * This function is typically used to add a new activity from the admin side.
     *
     * @param activityPostDto An object containing the details of the activity to be created.
     * @return A ResponseEntity containing a success message, HTTP status, and the details of the newly
     * created activity.
     */
    @PostMapping
    public ResponseEntity<Object> createActivity(@RequestBody ActivityPostDto activityPostDto) {
        ActivityPostDto createdActivity = activityService.createActivity(activityPostDto);
        return ResponseHandler.responseBuilder(ACTIVITY_DETAILS, HttpStatus.CREATED, createdActivity);
    }


    /**
     * Creates a new activity along with its associated category in the database.
     * This function is typically used to add a new activity and link it to an existing or new category
     * from the admin side.
     *
     * @param activityCateogryPostDto An object containing the details of the activity and its associated category.
     * @return A ResponseEntity containing a success message, HTTP status, and the details of the newly created
     * activity and category.
     */
    @PostMapping("/create-with-category")
    public ResponseEntity<Object> createActivityWithCategory(@RequestBody ActivityCateogryPostDto activityCateogryPostDto) {
        ActivityCateogryPostDto newActivityCategoryPostDto = activityService.createActivityWithCategory(activityCateogryPostDto);
        return ResponseHandler.responseBuilder(ACTIVITY_DETAILS_CATEGORY, HttpStatus.CREATED, newActivityCategoryPostDto);
    }


    /**
     * Retrieves a list of activities associated with a specific category name from the database.
     * This function is typically used to filter and display activities that belong to a particular category.
     *
     * @param categoryName The name of the category for which activities are to be retrieved.
     * @return A ResponseEntity containing a success message, HTTP status, and the list of activities
     * associated with the specified category.
     */
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Object> getActivitiesByCategoryName(@PathVariable String categoryName) {
        return ResponseHandler.responseBuilder(REQUESTED_ACTIVITY_DETAILS, HttpStatus.OK, activityService.getActivitiesByCategoryName(categoryName));
    }
}
