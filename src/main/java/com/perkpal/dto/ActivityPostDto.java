package com.perkpal.dto;

import lombok.Data;

@Data
public class ActivityPostDto {
    private String activityName;
    private Long categoryId;
    private String categoryDescription;
    private int weightagePerHour;
}

