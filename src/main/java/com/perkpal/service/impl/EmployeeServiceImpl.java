package com.perkpal.service.impl;

import com.perkpal.dto.*;
import com.perkpal.entity.Employee;
import com.perkpal.repository.EmployeeRepository;
import com.perkpal.repository.ParticipationRepository;
import com.perkpal.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perkpal.dto.EmployeeSummaryDto;

import java.util.*;
import java.util.stream.Collectors;


import java.sql.Timestamp;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ParticipationRepository participationRepository;
    @Override
    public List<EmployeeDto> getEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream().map(employee -> mapper.map(employee, EmployeeDto.class)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        // Handle the case when the employee is not found
        if (employee.isPresent()) {
            return mapper.map(employee.get(), EmployeeDto.class);
        } else {
            // Handle the case where the employee is not found
            return null; // or throw an exception, or return an empty DTO
        }
    }

    @Override
    public Employee updateEmployeePoints(Long id, EmployeeUpdatePointsDto employeeUpdatePointsDto) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employeeUpdatePointsDto.getTotalPoints() != null) {
            existingEmployee.setTotalPoints(employeeUpdatePointsDto.getTotalPoints());
        }
        if (employeeUpdatePointsDto.getRedeemablePoints() != null) {
            existingEmployee.setRedeemablePoints(employeeUpdatePointsDto.getRedeemablePoints());
        }

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public EmployeeDtoWithOnlyPoints getEmployeePointsById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            return mapper.map(employee.get(),EmployeeDtoWithOnlyPoints.class);
        }
        else{
            return null;
        }
    }

    @Override
    public EmployeeLoginInfoDto getEmployeeLoginInfoByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));

        // Map the necessary fields to EmployeeLoginInfoDto
        EmployeeLoginInfoDto employeeLoginInfoDto = new EmployeeLoginInfoDto();
        employeeLoginInfoDto.setId(employee.getId());
        employeeLoginInfoDto.setEmail(employee.getEmail());
        employeeLoginInfoDto.setRoleName(employee.getRoleId().getRoleName());
        employeeLoginInfoDto.setFirstName(employee.getFirstName());
        employeeLoginInfoDto.setLastName(employee.getLastName());
        employeeLoginInfoDto.setDuName(employee.getDuId().getDepartmentName());
        employeeLoginInfoDto.setPhotoUrl(employee.getPhotoUrl());
        employeeLoginInfoDto.setClubName(employee.getClubId().getClubName());
        return employeeLoginInfoDto;
    }

    @Override
    public List<EmployeeSummaryDto> getEmployeesByPointsInDateRange(Timestamp initialDate, Timestamp endDate) {
        return employeeRepository.findEmployeesByPointsInDateRange(initialDate, endDate);
    }

    @Override
    public List<EmployeeLeaderBoardDto> getSortedLeaderboard() {
        int currentYear = java.time.Year.now().getValue(); // Get the current year
        return participationRepository.findEmployeeLeaderboardByYear(currentYear);
    }

    @Override
    public List<EmployeeDto> getEmployeesByPointsInDateRangeWithEmployeeDto(Timestamp initialDate, Timestamp endDate) {
        // Fetch the points from the ParticipationRepository
        List<Object[]> results = participationRepository.findEmployeePointsInDateRange(initialDate, endDate);

        // Map the results to Employee entities
        Map<Long, Long> employeePointsMap = results.stream()
                .collect(Collectors.toMap(result -> (Long) result[0], result -> (Long) result[1]));

        // Fetch employees and sort them by points
        List<Employee> employees = employeeRepository.findAll().stream()
                .filter(employee -> employeePointsMap.containsKey(employee.getId()))
                .sorted((e1, e2) -> employeePointsMap.get(e2.getId()).compareTo(employeePointsMap.get(e1.getId())))
                .collect(Collectors.toList());

        // Convert to DTOs
        return employees.stream()
                .map(employee -> {
                    EmployeeDto employeeDto = mapper.map(employee, EmployeeDto.class);
                    employeeDto.setTotalPoints(employeePointsMap.get(employee.getId()));
                    return employeeDto;
                })
                .collect(Collectors.toList());
    }


}
