package com.perkpal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeParticipationDetailsDto {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String duDepartmentName;
    private String clubName;
    private String photoUrl;
    private List<ParticipationDetailDto> participations;
}
