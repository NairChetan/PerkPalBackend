package com.perkpal.service.impl;

import com.perkpal.dto.*;
import com.perkpal.entity.Employee;
import com.perkpal.repository.EmployeeRepository;
import com.perkpal.repository.ParticipationRepository;
import com.perkpal.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ParticipationRepository participationRepository;

    @Override
    /**
     * Retrieves a list of all employees.
     *
     * This method fetches all employees from the repository, maps each employee entity to an {@link EmployeeDto} object
     * using the {@link ModelMapper}, and returns the list of {@link EmployeeDto}.
     *
     * @return A {@link List} of {@link EmployeeDto} objects representing all employees.
     */
    public List<EmployeeDto> getEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream().map(employee -> mapper.map(employee, EmployeeDto.class)).collect(Collectors.toList());
    }

    @Override
    /**
     * Retrieves an employee by its ID.
     *
     * This method finds an employee by its ID. If the employee is found, it is mapped to an {@link EmployeeDto}.
     * If not found, the method returns null or you may choose to handle this case differently (e.g., throw an exception).
     *
     * @param id The ID of the employee to be retrieved.
     * @return An {@link EmployeeDto} representing the employee, or null if not found.
     */
    public EmployeeDto getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return mapper.map(employee.get(), EmployeeDto.class);
        } else {
            return null; // or throw an exception, or return an empty DTO
        }
    }

    @Override
    /**
     * Updates the points of an existing employee.
     *
     * This method updates the total and/or redeemable points of an employee based on the provided {@link EmployeeUpdatePointsDto}.
     * The method finds the employee by its ID, updates the points, and saves the changes in the repository.
     *
     * @param id The ID of the employee to be updated.
     * @param employeeUpdatePointsDto A {@link EmployeeUpdatePointsDto} object containing the updated points.
     * @return The updated {@link Employee} entity.
     */
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
    /**
     * Retrieves the points of an employee by its ID.
     *
     * This method finds an employee by its ID and maps the employee to an {@link EmployeeDtoWithOnlyPoints}.
     * If the employee is not found, the method returns null.
     *
     * @param id The ID of the employee whose points are to be retrieved.
     * @return An {@link EmployeeDtoWithOnlyPoints} containing the employee's points, or null if not found.
     */
    public EmployeeDtoWithOnlyPoints getEmployeePointsById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return mapper.map(employee.get(), EmployeeDtoWithOnlyPoints.class);
        } else {
            return null;
        }
    }

    @Override
    /**
     * Retrieves employee login information by email.
     *
     * This method finds an employee by email and maps the necessary fields to an {@link EmployeeLoginInfoDto}.
     * It throws an exception if the employee is not found.
     *
     * @param email The email of the employee whose login information is to be retrieved.
     * @return An {@link EmployeeLoginInfoDto} containing the employee's login information.
     */
    public EmployeeLoginInfoDto getEmployeeLoginInfoByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));

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
    /**
     * Retrieves a list of employees by points within a specified date range.
     *
     * This method retrieves employees whose points fall within the given date range. It uses the {@link EmployeeRepository}
     * to perform the query and returns a list of {@link EmployeeSummaryDto}.
     *
     * @param initialDate The start of the date range.
     * @param endDate The end of the date range.
     * @return A {@link List} of {@link EmployeeSummaryDto} representing employees with points in the specified date range.
     */
    public List<EmployeeSummaryDto> getEmployeesByPointsInDateRange(Timestamp initialDate, Timestamp endDate) {
        return employeeRepository.findEmployeesByPointsInDateRange(initialDate, endDate);
    }

    @Override
    /**
     * Retrieves the sorted leaderboard of employees for the current year.
     *
     * This method retrieves and returns a sorted leaderboard of employees for the current year from the {@link ParticipationRepository}.
     *
     * @return A {@link List} of {@link EmployeeLeaderBoardDto} representing the sorted leaderboard of employees.
     */
    public List<EmployeeLeaderBoardDto> getSortedLeaderboard() {
        int currentYear = java.time.Year.now().getValue(); // Get the current year
        return participationRepository.findEmployeeLeaderboardByYear(currentYear);
    }

    @Override
    /**
     * Retrieves a list of employees by points within a specified date range with detailed information.
     *
     * This method retrieves employee points within a specified date range from the {@link ParticipationRepository},
     * then fetches and sorts employees by these points. It maps the results to {@link EmployeeDto} and returns the list.
     *
     * @param initialDate The start of the date range.
     * @param endDate The end of the date range.
     * @return A {@link List} of {@link EmployeeDto} containing detailed information of employees sorted by points.
     */
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
