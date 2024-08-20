package com.perkpal.dto;
import lombok.Data;

@Data
public class ActivityPostDto {
    private String activityName;
    private Long categoryId;
    private int weightagePerHour;
    private String description;
    private Long createdBy;

}
