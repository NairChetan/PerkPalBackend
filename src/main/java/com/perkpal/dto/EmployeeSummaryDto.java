package com.perkpal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeSummaryDto {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String duDepartmentName;
    private Double totalPoints; // Use Double for floating-point numbers
    private String photoUrl;
}
