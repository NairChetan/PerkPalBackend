package com.perkpal.controller;

import com.perkpal.dto.ClubDto;
import com.perkpal.entity.Club;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.ClubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.perkpal.constants.Message.*;

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
    public ResponseEntity<Object> createClub(@Valid @RequestBody ClubDto clubDto) {
        ClubDto createdClub = clubService.createClub(clubDto);
        return ResponseHandler.responseBuilder(CLUB_CREATION, HttpStatus.CREATED, createdClub);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateClub(@PathVariable("id") Long id, @RequestBody Club club) {
        String response = clubService.updateClub(id, club);
        return ResponseHandler.responseBuilder(CLUB_UPDATION, HttpStatus.OK, response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClub(@PathVariable("id") Long id) {
        String response = clubService.deleteClub(id);
        return ResponseHandler.responseBuilder(CLUB_DELETION, HttpStatus.NO_CONTENT, null);
    }
}
