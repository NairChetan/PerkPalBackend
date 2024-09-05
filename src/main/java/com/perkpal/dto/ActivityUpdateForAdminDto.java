package com.perkpal.dto;

import lombok.Data;

@Data
public class ActivityUpdateForAdminDto {

    private String description;
    private int weightagePerHour;
    private String activityName;
    private Long updatedBy;
}
