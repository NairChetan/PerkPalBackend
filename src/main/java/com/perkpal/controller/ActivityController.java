package com.perkpal.controller;

import com.perkpal.dto.*;
import com.perkpal.entity.Activity;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseHandler.responseBuilder(ACTIVITY_DELETE, HttpStatus.NO_CONTENT, null);
    }


    /**
     * Updates an existing activity in the database.
     * This function is typically used to modify activity details from the admin side.
     *
     * @param id The ID of the activity to be updated.
     * @param activityUpdateForAdminDto An object containing the new details for the activity.
     * @return A ResponseEntity containing a success message, HTTP status, and the updated activity details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateActivity(@PathVariable Long id,
                                                 @RequestBody ActivityUpdateForAdminDto activityUpdateForAdminDto) {
        ActivityUpdateForAdminDto updatedActivity = activityService.updateActivity(id, activityUpdateForAdminDto);
        return ResponseHandler.responseBuilder("Activity updated successfully", HttpStatus.OK, updatedActivity);
    }
}
