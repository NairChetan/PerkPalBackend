package com.perkpal.controller;

import com.perkpal.constants.Message;
import com.perkpal.constants.Message.*;
import com.perkpal.dto.EmployeeDto;
import com.perkpal.dto.EmployeeUpdatePointsDto;
import com.perkpal.entity.Employee;
import com.perkpal.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   /* @Test
    *//**
     * Tests the retrieval of all employee accounts.
     *
     * This test ensures that when a GET request is made to fetch all employee accounts,
     * the controller returns a 200 OK status with a list of EmployeeDto objects.
     *
     * @see EmployeeController#getAccounts()
     *//*
    void givenEmployees_whenGetAccounts_thenReturnsOkStatus() {
        // Given: Mocked data and service behavior
        List<EmployeeDto> employees = Arrays.asList(new EmployeeDto(), new EmployeeDto());
        when(employeeService.getEmployees()).thenReturn(employees);

        // When: Simulate the GET request
        ResponseEntity<Object> response = employeeController.getAccounts();

        // Then: Assert the response status and verify service interaction
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(employeeService, times(1)).getEmployees();
    }

    @Test
    *//**
     * Tests the retrieval of a specific employee account by its ID.
     *
     * This test ensures that when a GET request is made with a valid employee ID,
     * the controller returns a 200 OK status with the corresponding EmployeeDto object.
     *
     * @see EmployeeController#getAccountWithId(Long)
     *//*
    void givenEmployeeId_whenGetAccountWithId_thenReturnsOkStatus() {
        // Given: Mocked data and service behavior
        Long id = 1L;
        EmployeeDto employeeDto = new EmployeeDto();
        when(employeeService.getEmployeeById(id)).thenReturn(employeeDto);

        // When: Simulate the GET request
        ResponseEntity<Object> response = employeeController.getAccountWithId(id);

        // Then: Assert the response status and verify service interaction
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(employeeService, times(1)).getEmployeeById(id);
    }

    @Test
    *//**
     * Tests the update of employee points.
     *
     * This test ensures that when a PUT request is made with a valid employee ID and EmployeeUpdatePointsDto,
     * the controller returns a 200 OK status indicating successful update of employee points.
     *
     * @see EmployeeController#updateEmployeePoints(Long, EmployeeUpdatePointsDto)
     *//*
    void givenEmployeeUpdatePointsDto_whenUpdateEmployeePoints_thenReturnsOkStatus() {
        // Given: Mocked data and service behavior
        Long id = 1L;
        EmployeeUpdatePointsDto updatePointsDto = new EmployeeUpdatePointsDto();
        when(employeeService.updateEmployeePoints(id, updatePointsDto)).thenReturn(new Employee());

        // When: Simulate the PUT request
        ResponseEntity<Object> response = employeeController.updateEmployeePoints(id, updatePointsDto);

        // Then: Assert the response status and verify service interaction
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(employeeService, times(1)).updateEmployeePoints(id, updatePointsDto);
    }*/
    @Test
    public void givenEmployeeServiceReturnsListOfEmployees_whenGetAccountsIsCalled_thenResponseContainsListOfEmployeesAndStatusIsOk() {
        // Arrange
        EmployeeDto employee1 = new EmployeeDto();
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setDesignation("Developer");
        employee1.setEmail("john.doe@example.com");
        employee1.setDuDepartmentName("Engineering");
        employee1.setRoleRoleName("Employee");
        employee1.setTotalPoints(100L);
        employee1.setRedeemablePoints(50L);
        employee1.setClubClubName("Tech Club");
        employee1.setPhotoUrl("http://example.com/photo1.jpg");

        EmployeeDto employee2 = new EmployeeDto();
        employee2.setFirstName("Jane");
        employee2.setLastName("Doe");
        employee2.setDesignation("Manager");
        employee2.setEmail("jane.doe@example.com");
        employee2.setDuDepartmentName("Engineering");
        employee2.setRoleRoleName("Manager");
        employee2.setTotalPoints(200L);
        employee2.setRedeemablePoints(150L);
        employee2.setClubClubName("Tech Club");
        employee2.setPhotoUrl("http://example.com/photo2.jpg");

        List<EmployeeDto> employeeList = Arrays.asList(employee1, employee2);
        when(employeeService.getEmployees()).thenReturn(employeeList);

        // Act
        ResponseEntity<Object> response = employeeController.getAccounts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Requested Employee Details are given here", responseBody.get("message"));
        assertEquals(HttpStatus.OK, responseBody.get("httpStatus"));
        assertEquals(employeeList, responseBody.get("data"));
        verify(employeeService, times(1)).getEmployees();
    }
    @Test
    public void givenValidEmployeeId_whenGetAccountWithIdIsCalled_thenResponseContainsEmployeeDetailsAndStatusIsOk() {
        // Arrange
        Long employeeId = 1L;
        EmployeeDto employee = new EmployeeDto();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDesignation("Developer");
        employee.setEmail("john.doe@example.com");
        employee.setDuDepartmentName("Engineering");
        employee.setRoleRoleName("Employee");
        employee.setTotalPoints(100L);
        employee.setRedeemablePoints(50L);
        employee.setClubClubName("Tech Club");
        employee.setPhotoUrl("http://example.com/photo1.jpg");

        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        // Act
        ResponseEntity<Object> response = employeeController.getAccountWithId(employeeId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(Message.REQUESTED_EMPLOYEE_DETAILS, responseBody.get("message"));
        assertEquals(HttpStatus.OK, responseBody.get("httpStatus"));
        assertEquals(employee, responseBody.get("data"));
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }



}
