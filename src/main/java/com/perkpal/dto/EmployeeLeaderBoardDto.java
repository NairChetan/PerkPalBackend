package com.perkpal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class EmployeeLeaderBoardDto {
    private Long employeeId;
    private String fullName;
    private String departmentName;
    private String photoUrl;
    private Double totalPoints;
}

