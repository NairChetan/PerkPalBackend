package com.perkpal.dto;

import lombok.Data;

@Data
public class ActivityDto {
    private String activityName;
    private String categoryCategoryName;
    private String categoryCategoryDescription;
    private int weightagePerHour;
}
