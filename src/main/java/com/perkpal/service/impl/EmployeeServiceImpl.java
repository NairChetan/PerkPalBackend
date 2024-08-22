package com.perkpal.service.impl;

import com.perkpal.dto.EmployeeDto;
import com.perkpal.dto.EmployeeDtoWithOnlyPoints;
import com.perkpal.dto.EmployeeUpdatePointsDto;
import com.perkpal.entity.Employee;
import com.perkpal.exception.ResourceNotFoundException;
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
}
