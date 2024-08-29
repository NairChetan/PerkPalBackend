package com.perkpal.controller;

import com.perkpal.dto.DuChartDataDto;
import com.perkpal.service.DuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/du")
public class DuController {

    @Autowired
    private DuService duService;


    /**
     * Retrieves DU points data within a specified date range.
     * The date range is provided as string parameters in the request.
     *
     * @param startDateStr The start date as a string, expected in the format "yyyy-MM-dd'T'HH:mm:ss".
     *                     The 'T' character is automatically replaced with a space for proper parsing.
     * @param endDateStr   The end date as a string, expected in the format "yyyy-MM-dd'T'HH:mm:ss".
     *                     The 'T' character is automatically replaced with a space for proper parsing.
     * @return A ResponseEntity containing a list of DuChartDataDto objects representing DU points data
     * within the specified date range, and an HTTP status code of 200 (OK).
     * @throws DateTimeParseException   if the provided date strings cannot be parsed into the expected format.
     * @throws IllegalArgumentException if the startDate or endDate is null, or if the startDate is after the endDate.
     */
    @GetMapping("/points")
    public ResponseEntity<List<DuChartDataDto>> getDuPointsInDateRange(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {

        // Adjust the incoming date strings by replacing 'T' with a space
        String formattedStartDateStr = startDateStr.replace('T', ' ');
        String formattedEndDateStr = endDateStr.replace('T', ' ');

        // Define the expected date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Parse the date strings to LocalDateTime
        LocalDateTime startDateTime = LocalDateTime.parse(formattedStartDateStr, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(formattedEndDateStr, formatter);

        // Convert to Timestamp
        Timestamp startDate = Timestamp.valueOf(startDateTime);
        Timestamp endDate = Timestamp.valueOf(endDateTime);

        // Fetch DU points
        List<DuChartDataDto> duChartData = duService.getDuPointsInDateRange(startDate, endDate);

        // Return the response
        return ResponseEntity.ok(duChartData);
    }
}
