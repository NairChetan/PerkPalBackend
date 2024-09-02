package com.perkpal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDto {
    private int clubId;
    private Long createdBy;
    private String clubName;
    private String clubDescription;
    private Long initialThreshold;
    private Long FinalThreshold;

}
