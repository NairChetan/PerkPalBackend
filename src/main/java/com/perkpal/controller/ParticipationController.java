package com.perkpal.controller;

import com.perkpal.dto.*;
import com.perkpal.entity.Participation;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.perkpal.constants.AppConstants.*;
import static com.perkpal.constants.Message.*;

@RestController
@RequestMapping("/api/v1/participation")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    /**
     * Handles HTTP POST requests for creating a new participation entry.
     * <p>
     * This method responds to POST requests at the base endpoint. It accepts a {@link ParticipationDto} object in the request body,
     * which contains the details of the participation to be created. The method then creates a new participation entry using
     * `participationService` and returns the created participation information wrapped in a standardized response structure.
     *
     * @param participationDto A {@link ParticipationDto} object containing the details of the participation to be created.
     * @return A {@link ResponseEntity} containing the response status and the details of the newly created participation.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#CREATED} indicating that a new resource has been successfully created.
     */
    @PostMapping
    public ResponseEntity<Object> createParticipation(@RequestBody ParticipationDto participationDto) {
        ParticipationDto newParticipation = participationService.createParticipation(participationDto);
        return ResponseHandler.responseBuilder(PARTICIPATION_CREATION, HttpStatus.CREATED, newParticipation);
    }

    /**
     * Handles HTTP GET requests for retrieving a participation entry by its ID.
     * <p>
     * This method responds to GET requests at the endpoint that includes the participation ID as a path variable.
     * It retrieves the participation details associated with the specified ID using `participationService` and returns
     * the information wrapped in a standardized response structure.
     *
     * @param id The ID of the participation entry to be retrieved. It is provided as a path variable in the URL.
     * @return A {@link ResponseEntity} containing the response status and the details of the requested participation.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#OK} indicating a successful request.
     */


    @GetMapping("/{id}")
    public ResponseEntity<Object> getParticipationById(@PathVariable Long id) {
        ParticipationDto participation = participationService.getParticipationById(id);
        return ResponseHandler.responseBuilder(PARTICIPATION_RETRIEVAL, HttpStatus.OK, participation);
    }

    /**
     * Handles HTTP GET requests for retrieving all participation entries.
     * <p>
     * This method responds to GET requests at the base endpoint. It retrieves all participation entries from
     * `participationService` and returns them wrapped in a standardized response structure.
     *
     * @return A {@link ResponseEntity} containing the response status and a list of all participation entries.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#OK} indicating a successful request. The list includes {@link ParticipationDto} objects
     * representing each participation entry.
     */


    @GetMapping
    public ResponseEntity<Object> getAllParticipations() {
        List<ParticipationDto> participations = participationService.getAllParticipations();
        return ResponseHandler.responseBuilder(PARTICIPATION_RETRIEVAL, HttpStatus.OK, participations);
    }

    /**
     * Handles HTTP PUT requests for updating an existing participation entry.
     * <p>
     * This method responds to PUT requests at the endpoint that includes the participation ID as a path variable.
     * It accepts a {@link ParticipationDto} object in the request body, which contains the updated details of the participation.
     * The method then updates the specified participation entry using `participationService` and returns the updated participation
     * information wrapped in a standardized response structure.
     *
     * @param id               The ID of the participation entry to be updated. It is provided as a path variable in the URL.
     * @param participationDto A {@link ParticipationDto} object containing the updated details of the participation.
     * @return A {@link ResponseEntity} containing the response status and the details of the updated participation.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#OK} indicating that the update was successful.
     */


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParticipation(@PathVariable Long id, @RequestBody ParticipationDto participationDto) {
        ParticipationDto updatedParticipation = participationService.updateParticipation(id, participationDto);
        return ResponseHandler.responseBuilder(PARTICIPATION_UPDATION, HttpStatus.OK, updatedParticipation);
    }

    /**
     * Handles HTTP DELETE requests for removing a participation entry by its ID.
     * <p>
     * This method responds to DELETE requests at the endpoint that includes the participation ID as a path variable.
     * It deletes the participation entry associated with the specified ID using `participationService` and returns
     * a response indicating the deletion status.
     *
     * @param id The ID of the participation entry to be deleted. It is provided as a path variable in the URL.
     * @return A {@link ResponseEntity} containing the response status. The response body is empty,
     * with a status of {@link HttpStatus#NO_CONTENT} indicating that the deletion was successful and there is no content to return.
     */


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParticipation(@PathVariable Long id) {
        participationService.deleteParticipation(id);
        return ResponseHandler.responseBuilder(PARTICIPATION_DELETION, HttpStatus.NO_CONTENT, null);
    }

    /**
     * Handles HTTP POST requests for creating a new participation entry.
     * <p>
     * This method responds to POST requests at the endpoint `/participationpost`. It accepts a {@link ParticipationPostDto} object
     * in the request body, which contains the details of the participation to be created. The method then creates the participation
     * entry using `participationService` and returns a response indicating the creation status.
     *
     * @param participationPostDto A {@link ParticipationPostDto} object containing the details of the participation to be created.
     * @return A {@link ResponseEntity} containing the response status and a success message.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#CREATED} indicating that the participation was successfully created.
     */


    @PostMapping("/participationpost")
    public ResponseEntity<Object> createParticipation(@RequestBody ParticipationPostDto participationPostDto) {
        participationService.createParticipation(participationPostDto);
        return ResponseHandler.responseBuilder(PARTICIPATION_CREATION, HttpStatus.CREATED, "Participation recorded successfully");
    }

    /**
     * Handles HTTP GET requests for retrieving participation entries pending approval with pagination and sorting.
     * <p>
     * This method responds to GET requests at the endpoint `/pending-approval`. It accepts query parameters for pagination and sorting,
     * including `pageNumber`, `pageSize`, `sortBy`, and `sortDir`. The method retrieves the paginated list of participation entries
     * pending approval using `participationService` and returns the data wrapped in a standardized response structure.
     *
     * @param pageNumber The page number of the results to retrieve. Defaults to 0 if not provided.
     * @param pageSize   The number of results per page. Defaults to 4 if not provided.
     * @param sortBy     The field by which to sort the results. Defaults to participationDate if not provided.
     * @param sortDir    The direction of sorting (ascending or descending). Defaults to desc if not provided.
     * @return A {@link ResponseEntity} containing the response status and a {@link PaginatedResponse} with the list of participation entries
     * pending approval. The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#OK} indicating a successful request.
     */


    @GetMapping("/pending-approval")
    public ResponseEntity<Object> getParticipationForPendingApproval(
            @RequestParam(value = PAGE_NUMBER, defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = SORT_BY, defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = SORT_DIRECTION, defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        PaginatedResponse<ParticipationDetailsFetchForPendingApprovalDto> paginatedResponse = participationService.getAllPendingApproval(pageNumber, pageSize, sortBy, sortDir);
        return ResponseHandler.responseBuilder(PARTICIPATION_RETRIEVAL, HttpStatus.OK, paginatedResponse);
    }

    /**
     * Handles HTTP PUT requests for updating the approval status and remarks of a participation entry.
     * <p>
     * This method responds to PUT requests at the endpoint that includes the participation ID as a path variable.
     * It accepts a {@link ParticipationApprovalStatusRemarksPostDto} object in the request body, which contains the updated
     * approval status and remarks for the participation. The method then updates the specified participation entry using
     * `participationService` and returns the updated participation information wrapped in a standardized response structure.
     *
     * @param id                                        The ID of the participation entry to be updated. It is provided as a path variable in the URL.
     * @param participationApprovalStatusRemarksPostDto A {@link ParticipationApprovalStatusRemarksPostDto} object containing the updated
     *                                                  approval status and remarks for the participation.
     * @return A {@link ResponseEntity} containing the response status and the details of the updated participation.
     * The response body is generated using {@link ResponseHandler#responseBuilder},
     * with a status of {@link HttpStatus#OK} indicating that the update was successful.
     */

    @PutMapping("/approval-status-remark/{id}")
    public ResponseEntity<Object> updateApprovalStatusAndRemark(@PathVariable Long id, @RequestBody ParticipationApprovalStatusRemarksPostDto participationApprovalStatusRemarksPostDto) {
        ParticipationApprovalStatusRemarksPostDto updatedParticipation = participationService.updateApprovalStatusAndRemark(id, participationApprovalStatusRemarksPostDto);
        return ResponseHandler.responseBuilder(PARTICIPATION_UPDATION, HttpStatus.OK, updatedParticipation);
    }

    /**
     * Handles HTTP GET requests for retrieving user logins by date and employee ID.
     * <p>
     * This method responds to GET requests at the endpoint `/date/{date}`. It accepts a date path variable and an employee ID query parameter.
     * It retrieves the user logins for the specified date and employee ID using `participationService` and returns the data as a list of
     * {@link ParticipationGetForUserLogDto} objects. The date should be provided in the format `yyyy-MM-dd`.
     *
     * @param dateStr    The date for which to retrieve user logins, provided as a path variable in `yyyy-MM-dd` format.
     * @param employeeId The ID of the employee whose logins are to be retrieved. This is provided as a query parameter.
     * @return A {@link List} of {@link ParticipationGetForUserLogDto} objects containing user logins for the specified date and employee ID.
     * @throws ResponseStatusException if the date format provided is invalid. A {@link HttpStatus#BAD_REQUEST} status is returned with
     *                                 an error message indicating the correct date format.
     */


    @GetMapping("/date/{date}")
    public List<ParticipationGetForUserLogDto> getUserLoginsByDate(
            @PathVariable("date") String dateStr,
            @RequestParam("employeeId") Long employeeId) {
        try {
            LocalDate date = LocalDate.parse(dateStr); // Assuming dateStr is in "yyyy-MM-dd" format
            return participationService.getUserLoginsByDateAndEmployeeId(date, employeeId);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Please use 'yyyy-MM-dd'.");
        }
    }

    /**
     * Handles HTTP GET requests for retrieving user logins by employee ID.
     * <p>
     * This method responds to GET requests at the endpoint `/date`. It accepts an employee ID query parameter and retrieves
     * all user logins for the specified employee using `participationService`. The method returns the data as a list of
     * {@link ParticipationGetForUserLogDto} objects.
     *
     * @param employeeId The ID of the employee whose logins are to be retrieved. This is provided as a query parameter.
     * @return A {@link List} of {@link ParticipationGetForUserLogDto} objects containing user logins for the specified employee.
     * @throws ResponseStatusException if the date format is invalid (not applicable here as no date is provided).
     */


    @GetMapping("/date")
    public List<ParticipationGetForUserLogDto> getUserLoginsByEmployeeId(
            @RequestParam("employeeId") Long employeeId) {
        try {
            return participationService.getUserLoginsByEmployeeId(employeeId);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Please use 'yyyy-MM-dd'.");
        }

    }

    @PutMapping("userLog/{id}")
    public ResponseEntity<Participation> updateParticipation(
            @PathVariable("id") Long id,
            @RequestBody ParticipationPutForUserLogDto participationPutForUserLogDto) {

        Participation updatedParticipation = participationService.updateParticipation(id, participationPutForUserLogDto);
        return new ResponseEntity<>(updatedParticipation, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchParticipations(
            @RequestParam(value = "activityName", required = false) String activityName,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "employeeId", required = false) Integer employeeId,
            @RequestParam(value = "approvalStatus", defaultValue = "pending", required = false) String approvalStatus,
            @RequestParam(value = PAGE_NUMBER, defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = SORT_BY, defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = SORT_DIRECTION, defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        PaginatedResponse<ParticipationDetailsFetchForPendingApprovalDto> paginatedResponse =
                participationService.searchParticipations(activityName, firstName, lastName, employeeId, approvalStatus, pageNumber, pageSize, sortBy, sortDir);

        return ResponseHandler.responseBuilder("Participation search successful", HttpStatus.OK, paginatedResponse);
    }
}
