package com.perkpal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationDetailsFetchForPendingApprovalDto {
    private Long id;
    private String employeeFirstName;
    private String employeeLastName;
    private Long employeeId;
    private String activityName;
    private String activityIdCategoryName;
    private Timestamp participationDate;
    private Timestamp approvalDate;
    private int duration;
    private String description;
    private String proofUrl;
    private String remarks;
}
