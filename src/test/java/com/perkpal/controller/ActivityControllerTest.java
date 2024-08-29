package com.perkpal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perkpal.dto.ActivityCateogryPostDto;
import com.perkpal.dto.ActivityGetBasedOnCategoryDto;
import com.perkpal.dto.ActivityPostDto;
import com.perkpal.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ActivityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(activityController).build();

        objectMapper = new ObjectMapper();
    }


    /**
     * Tests the {@link ActivityController#getActivities()} method when no activities are present.
     * Verifies that an empty list is returned with an HTTP status of OK (200).
     *
     * @throws Exception if an error occurs during the request execution
     */
    @Test
    public void givenNoActivities_whenGetActivities_thenReturnEmptyListAndOkStatus() throws Exception {
        // Given
        when(activityService.getActivity()).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<Object> responseEntity = activityController.getActivities();

        // Then
        // Extracting the body of the response
        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        // Assert the response structure and content
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseBody.get("data"));
        assertEquals("Requested Activity Details are given here", responseBody.get("message"));
        assertEquals(HttpStatus.OK, responseBody.get("httpStatus"));

        // Also verify using MockMvc
        mockMvc.perform(get("/api/v1/activity"))  // Replace with your actual endpoint
                .andExpect(status().isOk());
    }


    private ObjectMapper objectMapper;



    /**
     * Tests the {@link ActivityController#createActivity(ActivityPostDto)} method with a valid
     * {@link ActivityPostDto} object. Verifies that the created activity is returned with an HTTP status of CREATED (201).
     *
     * @throws Exception if an error occurs during the request execution
     */
    @Test
    public void givenValidActivityPostDto_whenCreateActivity_thenReturnCreatedActivityAndStatus201() throws Exception {
        // Given
        ActivityPostDto activityPostDto = new ActivityPostDto();
        activityPostDto.setActivityName("Sample Activity");
        activityPostDto.setCategoryId(1L);
        activityPostDto.setWeightagePerHour(10);
        activityPostDto.setDescription("Sample Description");
        activityPostDto.setCreatedBy(1L);

        ActivityPostDto createdActivityPostDto = new ActivityPostDto();
        createdActivityPostDto.setActivityName("Sample Activity");
        createdActivityPostDto.setCategoryId(1L);
        createdActivityPostDto.setWeightagePerHour(10);
        createdActivityPostDto.setDescription("Sample Description");
        createdActivityPostDto.setCreatedBy(1L);

        when(activityService.createActivity(activityPostDto)).thenReturn(createdActivityPostDto);

        // When
        ResponseEntity<Object> responseEntity = activityController.createActivity(activityPostDto);

        // Then
        // Extracting the body of the response
        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        // Assert the response structure and content
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdActivityPostDto, responseBody.get("data"));
        assertEquals("Activity created successfully", responseBody.get("message"));
        assertEquals(HttpStatus.CREATED, responseBody.get("httpStatus"));

        // Also verify using MockMvc
        mockMvc.perform(post("/api/v1/activity")  // Replace with your actual endpoint
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(activityPostDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.activityName").value("Sample Activity"))
                .andExpect(jsonPath("$.data.categoryId").value(1L))
                .andExpect(jsonPath("$.data.weightagePerHour").value(10))
                .andExpect(jsonPath("$.data.description").value("Sample Description"))
                .andExpect(jsonPath("$.data.createdBy").value(1L));
    }


    /**
     * Tests the {@link ActivityController#createActivityWithCategory(ActivityCateogryPostDto)} method with a valid
     * {@link ActivityCateogryPostDto} object. Verifies that the created activity with a new category is returned with
     * an HTTP status of CREATED (201).
     *
     * @throws Exception if an error occurs during the request execution
     */
    @Test
    public void givenValidActivityCategoryPostDto_whenCreateActivityWithCategory_thenReturnCreatedActivityAndStatus201() throws Exception {
        // Given
        ActivityCateogryPostDto activityCateogryPostDto = new ActivityCateogryPostDto();
        activityCateogryPostDto.setActivityName("Sample Activity");
        activityCateogryPostDto.setCategoryName("Sample Category");
        activityCateogryPostDto.setWeightagePerHour(10);
        activityCateogryPostDto.setDescription("Sample Description");
        activityCateogryPostDto.setCreatedBy(1L);

        ActivityCateogryPostDto createdActivityCategoryPostDto = new ActivityCateogryPostDto();
        createdActivityCategoryPostDto.setActivityName("Sample Activity");
        createdActivityCategoryPostDto.setCategoryName("Sample Category");
        createdActivityCategoryPostDto.setWeightagePerHour(10);
        createdActivityCategoryPostDto.setDescription("Sample Description");
        createdActivityCategoryPostDto.setCreatedBy(1L);

        when(activityService.createActivityWithCategory(activityCateogryPostDto)).thenReturn(createdActivityCategoryPostDto);

        // When
        ResponseEntity<Object> responseEntity = activityController.createActivityWithCategory(activityCateogryPostDto);

        // Extracting the body of the response
        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        // Then
        // Assert the response structure and content
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdActivityCategoryPostDto, responseBody.get("data"));
        assertEquals("Activity with category created successfully", responseBody.get("message"));
        assertEquals(HttpStatus.CREATED, responseBody.get("httpStatus"));

        // Also verify using MockMvc
        mockMvc.perform(post("/api/v1/activity/create-with-category")  // Replace with your actual endpoint
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(activityCateogryPostDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.activityName").value("Sample Activity"))
                .andExpect(jsonPath("$.data.categoryName").value("Sample Category"))
                .andExpect(jsonPath("$.data.weightagePerHour").value(10))
                .andExpect(jsonPath("$.data.description").value("Sample Description"))
                .andExpect(jsonPath("$.data.createdBy").value(1L));
    }

    /**
     * Tests the {@link ActivityController#getActivitiesByCategoryName(String)} method with a valid category name.
     * Verifies that a list of activities for the given category is returned with an HTTP status of OK (200).
     *
     * @throws Exception if an error occurs during the request execution
     */
    @Test
    public void givenCategoryName_whenGetActivitiesByCategoryName_thenReturnActivitiesAndStatus200() throws Exception {
        // Given
        String categoryName = "Sample Category";

        ActivityGetBasedOnCategoryDto activity1 = new ActivityGetBasedOnCategoryDto();
        activity1.setId(1L);
        activity1.setActivityName("Activity 1");
        activity1.setCategoryName(categoryName);

        ActivityGetBasedOnCategoryDto activity2 = new ActivityGetBasedOnCategoryDto();
        activity2.setId(2L);
        activity2.setActivityName("Activity 2");
        activity2.setCategoryName(categoryName);

        List<ActivityGetBasedOnCategoryDto> activities = Arrays.asList(activity1, activity2);

        when(activityService.getActivitiesByCategoryName(categoryName)).thenReturn(activities);

        // When
        ResponseEntity<Object> responseEntity = activityController.getActivitiesByCategoryName(categoryName);

        // Extracting the body of the response
        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        // Then
        // Assert the response structure and content
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(activities, responseBody.get("data"));
        assertEquals("Requested Activity Details are given here", responseBody.get("message"));
        assertEquals(HttpStatus.OK, responseBody.get("httpStatus"));

        // Also verify using MockMvc
        mockMvc.perform(get("/api/v1/activity/category/" + categoryName))  // Replace with your actual endpoint
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].activityName").value("Activity 1"))
                .andExpect(jsonPath("$.data[0].categoryName").value(categoryName))
                .andExpect(jsonPath("$.data[1].id").value(2L))
                .andExpect(jsonPath("$.data[1].activityName").value("Activity 2"))
                .andExpect(jsonPath("$.data[1].categoryName").value(categoryName));
    }
}
