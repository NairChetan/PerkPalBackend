package com.perkpal.service;

import com.perkpal.dto.*;
import com.perkpal.entity.Employee;
import com.perkpal.dto.EmployeeSummaryDto;

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
}

