package com.perkpal.dto;

import lombok.Data;

@Data
public class ActivityDto {
    private Long id;
    private String activityName;
    private String categoryName;
    private String categoryDescription;
    private String description;
    private int weightagePerHour;
}
