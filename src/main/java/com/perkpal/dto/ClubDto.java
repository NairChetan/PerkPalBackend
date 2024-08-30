package com.perkpal.dto;

import lombok.Data;

@Data
public class ClubDto {
    private Long createdBy;
    private String clubName;
    private String clubDescription;
    private Long initialThreshold;
    private Long FinalThreshold;

}
