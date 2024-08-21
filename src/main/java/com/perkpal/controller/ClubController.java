package com.perkpal.controller;

import com.perkpal.dto.ClubDto;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.ClubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.perkpal.constants.Message.REQUESTED_CLUB_DETAILS;

@RestController
@RequestMapping("/api/v1/club")
public class ClubController {
    @Autowired
    private ClubService clubService;

    @GetMapping
    public ResponseEntity<Object> getClubs() {
        return ResponseHandler.responseBuilder(REQUESTED_CLUB_DETAILS,
                HttpStatus.OK, clubService.getClubs());
    }

    @PostMapping
    public ResponseEntity<ClubDto> createClub(@Valid @RequestBody ClubDto clubDto){
        return new ResponseEntity<>(clubService.createClub(clubDto), HttpStatus.CREATED);
    }


}
