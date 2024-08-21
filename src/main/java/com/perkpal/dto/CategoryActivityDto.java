package com.perkpal.dto;

import lombok.Data;

@Data
public class CategoryActivityDto {
    private Long id;
    private String activityName;
    private int weightagePerHour;

}
