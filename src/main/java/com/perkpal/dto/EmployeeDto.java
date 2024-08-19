package com.perkpal.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String designation;
    private String email;
    private String duDepartmentName;
    private String roleRoleName;
    private Long totalPoints;
    private Long redeemablePoints;
    private String clubClubName;
    private String photoUrl;
}
