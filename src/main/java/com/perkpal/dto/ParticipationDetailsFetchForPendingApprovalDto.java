package com.perkpal.dto;

import lombok.Data;

@Data
public class ParticipationDetailsFetchForPendingApprovalDto {
    private String employeeFirstName;
    private String employeeLastName;
    private Long employeeId;
    private String activityName;
    private String activityIdCategoryName;
    private int duration;
    private String description;
}
