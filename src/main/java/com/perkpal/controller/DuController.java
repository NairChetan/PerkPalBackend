package com.perkpal.controller;

import com.perkpal.dto.DuChartDataDto;
import com.perkpal.service.DuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/du")
public class DuController {

    @Autowired
    private DuService duService;

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
