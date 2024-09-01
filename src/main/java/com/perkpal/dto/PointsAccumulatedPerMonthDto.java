package com.perkpal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointsAccumulatedPerMonthDto {
    private int month;
    private double pointsAccumulated;
}
