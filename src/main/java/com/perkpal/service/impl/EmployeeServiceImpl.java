package com.perkpal.service.impl;

import com.perkpal.dto.EmployeeDto;
import com.perkpal.dto.EmployeeDtoWithOnlyPoints;
import com.perkpal.dto.EmployeeLoginInfoDto;
import com.perkpal.dto.EmployeeUpdatePointsDto;
import com.perkpal.entity.Employee;
import com.perkpal.repository.EmployeeRepository;
import com.perkpal.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private EmployeeRepository employeeRepository;
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

        return employeeLoginInfoDto;
    }


}
