package com.perkpal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointsAccumulatedOverYearsDto {
    private int year;
    private double pointsAccumulated;
}
