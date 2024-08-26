    package com.perkpal.controller;

    import com.perkpal.dto.EmployeeDto;
    import com.perkpal.dto.EmployeeLeaderBoardDto;
    import com.perkpal.dto.EmployeeLoginInfoDto;
    import com.perkpal.dto.EmployeeSummaryDto;
    import com.perkpal.dto.EmployeeUpdatePointsDto;
    import com.perkpal.response.ResponseHandler;
    import com.perkpal.service.EmployeeService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import io.swagger.v3.oas.annotations.Parameter;

    import java.util.List;

    import static com.perkpal.constants.Message.*;
    import java.sql.Timestamp;
    import java.util.List;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;

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
        @GetMapping("/{id}/get-points")
        public ResponseEntity<Object> getAccountPointsWithId(@PathVariable(name = "id") Long id) {
            return ResponseHandler.responseBuilder(REQUESTED_EMPLOYEE_DETAILS, HttpStatus.OK, employeeService.getEmployeePointsById(id));
        }
        @GetMapping("/login-info")
        public ResponseEntity<Object> getEmployeeLoginInfoByEmail(@RequestParam(name = "email") String email) {
            EmployeeLoginInfoDto employeeLoginInfoDto = employeeService.getEmployeeLoginInfoByEmail(email);
            return ResponseHandler.responseBuilder(EMPLOYEE_ROLE_RETRIEVED, HttpStatus.OK, employeeLoginInfoDto);
        }

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

        @GetMapping("/leaderboard")
        public ResponseEntity<Object> getEmployeeLeaderboard() {
            List<EmployeeLeaderBoardDto> leaderboard = employeeService.getSortedLeaderboard();
            return ResponseHandler.responseBuilder(LEADERBOARD_RETRIEVED, HttpStatus.OK, leaderboard.subList(0, Math.min(3, leaderboard.size())));
        }


    }
