package com.perkpal.service;

import com.perkpal.dto.*;
import com.perkpal.entity.Employee;

import java.sql.Timestamp;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getEmployees();

    EmployeeDto getEmployeeById(Long id);

    Employee updateEmployeePoints(Long id, EmployeeUpdatePointsDto employeeUpdatePointsDto);

    EmployeeDtoWithOnlyPoints getEmployeePointsById(Long id);

    EmployeeLoginInfoDto getEmployeeLoginInfoByEmail(String email);

    List<EmployeeSummaryDto> getEmployeesByPointsInDateRange(Timestamp initialDate, Timestamp endDate);

    List<EmployeeLeaderBoardDto> getSortedLeaderboard();

    List<EmployeeDto> getEmployeesByPointsInDateRangeWithEmployeeDto(Timestamp initialDate, Timestamp endDate);

    List<PointsAccumulatedOverYearsDto> getApprovedPointsForLastFourYears(Long empId);

    /**
     * Retrieves a list of employees by activity within a specified date range.
     *
     * @param activityName The ID of the activity.
     * @param initialDate The start of the date range.
     * @param endDate The end of the date range.
     * @return A {@link List} of {@link EmployeeActivitySummaryDto} containing detailed information of employees.
     */
    List<EmployeeParticipationDetailsDto> getEmployeeParticipationDetailsByActivityAndDateRange(
            Timestamp initialDate, Timestamp endDate, String activityName);
}