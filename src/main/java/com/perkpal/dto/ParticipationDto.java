package com.perkpal.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ParticipationDto {
    private Long activityId;
    private int duration;
    private String remarks;
    private Timestamp participationDate;
    private Timestamp approvalDate;
    private Long approvedBy;
    private boolean approvalStatus;
}
