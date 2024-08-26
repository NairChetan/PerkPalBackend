package com.perkpal.dto;

import lombok.Data;

@Data
public class EmployeeAdminLeaderboardDto {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String duDepartmentName;
    private Long totalPoints;
    private String photoUrl;
}
