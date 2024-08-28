package com.perkpal.controller;

import com.perkpal.dto.*;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.perkpal.constants.Message.*;
import static com.perkpal.constants.AppConstants.*;

@RestController
@RequestMapping("/api/v1/participation")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @PostMapping
    public ResponseEntity<Object> createParticipation(@RequestBody ParticipationDto participationDto) {
        ParticipationDto newParticipation = participationService.createParticipation(participationDto);
        return ResponseHandler.responseBuilder(PARTICIPATION_CREATION, HttpStatus.CREATED, newParticipation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getParticipationById(@PathVariable Long id) {
        ParticipationDto participation = participationService.getParticipationById(id);
        return ResponseHandler.responseBuilder(PARTICIPATION_RETRIEVAL, HttpStatus.OK, participation);
    }

    @GetMapping
    public ResponseEntity<Object> getAllParticipations() {
        List<ParticipationDto> participations = participationService.getAllParticipations();
        return ResponseHandler.responseBuilder(PARTICIPATION_RETRIEVAL, HttpStatus.OK, participations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParticipation(@PathVariable Long id, @RequestBody ParticipationDto participationDto) {
        ParticipationDto updatedParticipation = participationService.updateParticipation(id, participationDto);
        return ResponseHandler.responseBuilder(PARTICIPATION_UPDATION, HttpStatus.OK, updatedParticipation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParticipation(@PathVariable Long id) {
        participationService.deleteParticipation(id);
        return ResponseHandler.responseBuilder(PARTICIPATION_DELETION, HttpStatus.NO_CONTENT, null);
    }
    @PostMapping("/participationpost")
    public ResponseEntity<Object> createParticipation(@RequestBody ParticipationPostDto participationPostDto) {
        participationService.createParticipation(participationPostDto);
        return ResponseHandler.responseBuilder(PARTICIPATION_CREATION, HttpStatus.CREATED, "Participation recorded successfully");
    }
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
    @PutMapping("/approval-status-remark/{id}")
    public ResponseEntity<Object> updateApprovalStatusAndRemark(@PathVariable Long id, @RequestBody ParticipationApprovalStatusRemarksPostDto participationApprovalStatusRemarksPostDto){
        ParticipationApprovalStatusRemarksPostDto updatedParticipation = participationService.updateApprovalStatusAndRemark(id,participationApprovalStatusRemarksPostDto);
        return ResponseHandler.responseBuilder(PARTICIPATION_UPDATION,HttpStatus.OK,updatedParticipation);
    }

    @Autowired
    private ParticipationService participationService1;


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

        @GetMapping("/date")
        public List<ParticipationGetForUserLogDto> getUserLoginsByEmployeeId(
                @RequestParam("employeeId") Long employeeId) {
            try {
                return participationService.getUserLoginsByEmployeeId(employeeId);
            } catch (DateTimeParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Please use 'yyyy-MM-dd'.");
            }



//    @GetMapping("/date/{date}")
//    public List<ParticipationGetForUserLogDto> getUserLoginsByDate(@PathVariable("date") String dateStr) {
//        try {
//            // Parse the date string into a LocalDate
//            LocalDate date = LocalDate.parse(dateStr); // Assuming dateStr is in "yyyy-MM-dd" format
//            return participationService1.getUserLoginsByDate(date);
//        } catch (DateTimeParseException e) {
//            // Handle the error (e.g., return an error response)
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Please use 'yyyy-MM-dd'.");
//        }
//    }
//@GetMapping("/date/{date}")
//public List<ParticipationGetForUserLogDto> getUserLoginsByDate(
//        @PathVariable("date") String dateStr,
//        @RequestParam("employeeId") Long employeeId) {
//    try {
//        LocalDate date = LocalDate.parse(dateStr); // Adjust format if needed
//        return participationService.getUserLoginsByDate(date, employeeId);
//    } catch (DateTimeParseException e) {
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format.");
//    }
//}



}}
