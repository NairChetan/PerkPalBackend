package com.perkpal.service;

import com.perkpal.dto.EmployeeDto;
import com.perkpal.dto.EmployeeDtoWithOnlyPoints;
import com.perkpal.dto.EmployeeLoginInfoDto;
import com.perkpal.dto.EmployeeUpdatePointsDto;
import com.perkpal.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getEmployees();
    EmployeeDto getEmployeeById(Long id);
    Employee updateEmployeePoints(Long id, EmployeeUpdatePointsDto employeeUpdatePointsDto);
    EmployeeDtoWithOnlyPoints getEmployeePointsById(Long id);
    EmployeeLoginInfoDto getEmployeeLoginInfoByEmail(String email);
}
