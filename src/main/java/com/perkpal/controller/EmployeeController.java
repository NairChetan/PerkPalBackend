package com.perkpal.controller;

import com.perkpal.dto.EmployeeUpdatePointsDto;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.perkpal.constants.Message.EMPLOYEE_POINTS_UPDATION;
import static com.perkpal.constants.Message.REQUESTED_EMPLOYEE_DETAILS;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Object> getAccounts() {
        return ResponseHandler.responseBuilder(REQUESTED_EMPLOYEE_DETAILS,
                HttpStatus.OK, employeeService.getEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountWithId(@PathVariable(name = "id") Long id) {
        return ResponseHandler.responseBuilder(REQUESTED_EMPLOYEE_DETAILS, HttpStatus.OK, employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}/points")
    public ResponseEntity<Object> updateEmployeePoints(@PathVariable(name = "id") Long id,
                                                       @RequestBody EmployeeUpdatePointsDto employeeUpdatePointsDto) {
        return ResponseHandler.responseBuilder(EMPLOYEE_POINTS_UPDATION , HttpStatus.OK,
                employeeService.updateEmployeePoints(id, employeeUpdatePointsDto));
    }

}
