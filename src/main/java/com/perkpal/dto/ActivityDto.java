package com.perkpal.dto;

import lombok.Data;

@Data
public class ActivityDto {
    private String activityName;
    private String categoryName;
    private String categoryDescription;
    private int weightagePerHour;
}
