package com.perkpal.service.impl;

import com.perkpal.dto.EmployeeDto;
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
        return mapper.map(employee,EmployeeDto.class);
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
}
