package com.perkpal.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ParticipationApprovalStatusRemarksPostDto {
    private String approvalStatus;
    private String remarks;
    private Timestamp approvalDate;

}
