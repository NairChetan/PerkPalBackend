package com.perkpal.dto;

import lombok.Data;

@Data
public class ActivityCateogryPostDto {
    private String activityName;
    private Long categoryId;          // Optional: Use this if you're linking to an existing category
    private String categoryName;      // Include this if creating a new category
    private int weightagePerHour;
    private String description;
    private Long createdBy;
    private Long categoryCreatedBy;
}
