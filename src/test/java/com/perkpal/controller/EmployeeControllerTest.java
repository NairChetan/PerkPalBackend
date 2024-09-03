package com.perkpal.controller;

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

    @Test
    /**
     * Tests the retrieval of all employee accounts.
     *
     * This test ensures that when a GET request is made to fetch all employee accounts,
     * the controller returns a 200 OK status with a list of EmployeeDto objects.
     *
     * @see EmployeeController#getAccounts()
     */
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
    /**
     * Tests the retrieval of a specific employee account by its ID.
     *
     * This test ensures that when a GET request is made with a valid employee ID,
     * the controller returns a 200 OK status with the corresponding EmployeeDto object.
     *
     * @see EmployeeController#getAccountWithId(Long)
     */
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
    /**
     * Tests the update of employee points.
     *
     * This test ensures that when a PUT request is made with a valid employee ID and EmployeeUpdatePointsDto,
     * the controller returns a 200 OK status indicating successful update of employee points.
     *
     * @see EmployeeController#updateEmployeePoints(Long, EmployeeUpdatePointsDto)
     */
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
    }
}
