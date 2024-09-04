package com.perkpal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ParticipationDetailDto {
    private Long participationId;
    private String activityName;
    private int duration;
    private String remarks;
    private Timestamp participationDate;
    private Timestamp approvalDate;
    private String description;
    private String proofUrl;
}