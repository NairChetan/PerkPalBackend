package com.perkpal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeActivitySummaryDto {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String designation;
    private String email;
    private Double totalPoints;
    private String photoUrl;
    private String duDepartmentName; // Added field for DU Department Name
}
