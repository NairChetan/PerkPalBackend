package com.perkpal.dto;


import lombok.Data;

@Data
public class ActivityGetBasedOnCategoryDto {

    private Long id;
    private String activityName;
    private String categoryName;
}
