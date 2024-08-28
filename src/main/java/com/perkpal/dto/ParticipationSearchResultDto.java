package com.perkpal.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ParticipationSearchResultDto {
    private Long id;
    private String employeeFirstName;
    private String employeeLastName;
    private Long employeeId;
    private String activityName;

}
