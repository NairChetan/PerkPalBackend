package com.perkpal.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ParticipationDetailsFetchForPendingApprovalDto {
    private Long id;
    private String employeeFirstName;
    private String employeeLastName;
    private Long employeeId;
    private String activityName;
    private String activityIdCategoryName;
    private Timestamp participationDate;
    private int duration;
    private String description;
}
