package com.perkpal.controller;

import com.perkpal.dto.*;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.EmployeeService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.perkpal.constants.Message.*;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * Handles HTTP GET requests for retrieving a list of employee accounts.
     * <p>
     * This method responds to GET requests to the endpoint associated with this controller.
     * It uses the `employeeService` to fetch the details of all employees and returns
     * the response wrapped in a standardized response structure.
     *
     * @return A {@link ResponseEntity} containing the response status and the list of employees.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#OK} indicating a successful request.
     */
    @GetMapping
    public ResponseEntity<Object> getAccounts() {
        return ResponseHandler.responseBuilder(REQUESTED_EMPLOYEE_DETAILS,
                HttpStatus.OK, employeeService.getEmployees());
    }

    /**
     * Handles HTTP GET requests for retrieving the details of a specific employee account by its ID.
     * <p>
     * This method responds to GET requests to the endpoint that includes the employee ID as a path variable.
     * It uses the `employeeService` to fetch the details of the employee with the specified ID and returns
     * the response wrapped in a standardized response structure.
     *
     * @param id The ID of the employee whose details are to be retrieved. It is provided as a path variable in the URL.
     * @return A {@link ResponseEntity} containing the response status and the details of the requested employee.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#OK} indicating a successful request.
     */

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountWithId(@PathVariable(name = "id") Long id) {
        return ResponseHandler.responseBuilder(REQUESTED_EMPLOYEE_DETAILS, HttpStatus.OK, employeeService.getEmployeeById(id));
    }

    /**
     * Handles HTTP PUT requests for updating the points of a specific employee.
     * <p>
     * This method responds to PUT requests to the endpoint that includes the employee ID as a path variable.
     * It updates the points of the employee with the specified ID based on the information provided in the
     * request body, which is encapsulated in an {@link EmployeeUpdatePointsDto} object. The updated
     * employee information is then returned wrapped in a standardized response structure.
     *
     * @param id                      The ID of the employee whose points are to be updated. It is provided as a path variable in the URL.
     * @param employeeUpdatePointsDto An {@link EmployeeUpdatePointsDto} object containing the new points information
     *                                to be updated for the employee. It is provided in the request body.
     * @return A {@link ResponseEntity} containing the response status and the result of the points update operation.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#OK} indicating a successful update.
     */
    @PutMapping("/{id}/points")
    public ResponseEntity<Object> updateEmployeePoints(@PathVariable(name = "id") Long id,
                                                       @RequestBody EmployeeUpdatePointsDto employeeUpdatePointsDto) {
        return ResponseHandler.responseBuilder(EMPLOYEE_POINTS_UPDATION, HttpStatus.OK,
                employeeService.updateEmployeePoints(id, employeeUpdatePointsDto));
    }

    /**
     * Handles HTTP GET requests for retrieving the points of a specific employee by their ID.
     * <p>
     * This method responds to GET requests at the endpoint that includes the employee ID as a path variable.
     * It retrieves the points of the employee with the specified ID using the `employeeService` and returns
     * the response wrapped in a standardized response structure.
     *
     * @param id The ID of the employee whose points are to be retrieved. It is provided as a path variable in the URL.
     * @return A {@link ResponseEntity} containing the response status and the points of the requested employee.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#OK} indicating a successful request.
     */
    @GetMapping("/{id}/get-points")
    public ResponseEntity<Object> getAccountPointsWithId(@PathVariable(name = "id") Long id) {
        return ResponseHandler.responseBuilder(REQUESTED_EMPLOYEE_DETAILS, HttpStatus.OK, employeeService.getEmployeePointsById(id));
    }

    /**
     * Handles HTTP GET requests for retrieving the login information of an employee based on their email address.
     * <p>
     * This method responds to GET requests at the endpoint that includes the email address as a query parameter.
     * It retrieves the login information of the employee associated with the specified email using the `employeeService`
     * and returns the response wrapped in a standardized response structure.
     *
     * @param email The email address of the employee whose login information is to be retrieved. It is provided as a query parameter in the URL.
     * @return A {@link ResponseEntity} containing the response status and the login information of the requested employee.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#OK} indicating a successful request.
     */
    @GetMapping("/login-info")
    public ResponseEntity<Object> getEmployeeLoginInfoByEmail(@RequestParam(name = "email") String email) {
        EmployeeLoginInfoDto employeeLoginInfoDto = employeeService.getEmployeeLoginInfoByEmail(email);
        return ResponseHandler.responseBuilder(EMPLOYEE_ROLE_RETRIEVED, HttpStatus.OK, employeeLoginInfoDto);
    }

    /**
     * Handles HTTP GET requests for retrieving a list of employees based on their points within a specified date range.
     * <p>
     * This method responds to GET requests at the endpoint `/api/v1/employees/by-points`. It accepts two query parameters
     * for the date range, `initialDate` and `endDate`, which are used to filter employees based on the points they accumulated
     * within the specified period. The dates must be provided in the format `yyyy-MM-dd'T'HH:mm:ss`. The method parses these
     * dates and retrieves the employee data using `employeeService`, then returns the data as a list of {@link EmployeeSummaryDto} objects.
     *
     * @param initialDateStr The start date of the date range in `yyyy-MM-dd'T'HH:mm:ss` format. This parameter is used to
     *                       filter employees based on the points accumulated from this date onward.
     * @param endDateStr     The end date of the date range in `yyyy-MM-dd'T'HH:mm:ss` format. This parameter is used to filter
     *                       employees based on the points accumulated up to this date.
     * @return A {@link List} of {@link EmployeeSummaryDto} objects representing employees who have points within the specified date range.
     * Each {@link EmployeeSummaryDto} contains summary information about the employee.
     * @throws RuntimeException if the date format provided is invalid or cannot be parsed.
     */
    @GetMapping("/api/v1/employees/by-points")
    public List<EmployeeSummaryDto> getEmployeesByPoints(
            @Parameter(description = "Initial date in format yyyy-MM-dd'T'HH:mm:ss", example = "2024-08-01T00:00:00")
            @RequestParam("initialDate") String initialDateStr,
            @Parameter(description = "End date in format yyyy-MM-dd'T'HH:mm:ss", example = "2024-08-25T23:59:59")
            @RequestParam("endDate") String endDateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Timestamp initialDate = new Timestamp(dateFormat.parse(initialDateStr).getTime());
            Timestamp endDate = new Timestamp(dateFormat.parse(endDateStr).getTime());
            return employeeService.getEmployeesByPointsInDateRange(initialDate, endDate);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format", e);
        }
    }

    /**
     * Handles HTTP GET requests for retrieving the top employees based on the leaderboard rankings.
     * <p>
     * This method responds to GET requests at the endpoint `/leaderboard`. It retrieves the sorted leaderboard from
     * the `employeeService`, which contains employee ranking information. The method then returns the top three employees,
     * or fewer if the leaderboard contains fewer than three entries, wrapped in a standardized response structure.
     *
     * @return A {@link ResponseEntity} containing the response status and a list of the top employees from the leaderboard.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#OK} indicating a successful request. The list includes up to three
     * {@link EmployeeLeaderBoardDto} objects representing the top employees.
     */
    @GetMapping("/leaderboard")
    public ResponseEntity<Object> getEmployeeLeaderboard() {
        List<EmployeeLeaderBoardDto> leaderboard = employeeService.getSortedLeaderboard();
        return ResponseHandler.responseBuilder(LEADERBOARD_RETRIEVED, HttpStatus.OK, leaderboard.subList(0, Math.min(3, leaderboard.size())));
    }

    /**
     * Handles HTTP GET requests for retrieving detailed information about employees based on their points within a specified date range.
     * <p>
     * This method responds to GET requests at the endpoint `/api/v1/employees/by-points-full-details`. It accepts two query parameters,
     * `initialDate` and `endDate`, which define the date range for filtering employees based on the points they accumulated within that period.
     * The dates should be provided in the format `yyyy-MM-dd'T'HH:mm:ss`. The method parses these dates, retrieves detailed employee information
     * using `employeeService`, and returns a list of {@link EmployeeDto} objects containing the full details of employees who meet the criteria.
     *
     * @param initialDateStr The start date of the date range in `yyyy-MM-dd'T'HH:mm:ss` format. This parameter is used to filter employees
     *                       based on the points accumulated from this date onward.
     * @param endDateStr     The end date of the date range in `yyyy-MM-dd'T'HH:mm:ss` format. This parameter is used to filter employees
     *                       based on the points accumulated up to this date.
     * @return A {@link List} of {@link EmployeeDto} objects containing detailed information about employees with points
     * within the specified date range. Each {@link EmployeeDto} provides full details of an employee.
     * @throws RuntimeException if the date format provided is invalid or cannot be parsed.
     */
    @GetMapping("/api/v1/employees/by-points-full-details")
    public List<EmployeeDto> getEmployeesByPointsWithFullEmployeeDetails(
            @RequestParam("initialDate") String initialDateStr,
            @RequestParam("endDate") String endDateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Timestamp initialDate = new Timestamp(dateFormat.parse(initialDateStr).getTime());
            Timestamp endDate = new Timestamp(dateFormat.parse(endDateStr).getTime());
            return employeeService.getEmployeesByPointsInDateRangeWithEmployeeDto(initialDate, endDate);
        } catch (ParseException e) {
            // Handle date parsing error
            throw new RuntimeException("Invalid date format", e);
        }
    }


}
