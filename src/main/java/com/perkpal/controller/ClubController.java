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


    /**
     * Retrieves a list of all clubs from the database.
     * This function is typically used to fetch all available clubs for display on the frontend.
     *
     * @return A ResponseEntity containing a success message, HTTP status, and the list of clubs
     * retrieved from the database.
     */
    @GetMapping
    public ResponseEntity<Object> getClubs() {
        return ResponseHandler.responseBuilder(REQUESTED_CLUB_DETAILS,
                HttpStatus.OK, clubService.getClubs());
    }


    /**
     * Creates a new club in the database.
     * This function is typically used to add a new club from the admin side.
     *
     * @param clubDto An object containing the details of the club to be created. The request body is validated.
     * @return A ResponseEntity containing a success message, HTTP status, and the details of the newly
     * created club.
     */
    @PostMapping
    public ResponseEntity<Object> createClub(@Valid @RequestBody ClubDto clubDto) {
        ClubDto createdClub = clubService.createClub(clubDto);
        return ResponseHandler.responseBuilder(CLUB_CREATION, HttpStatus.CREATED, createdClub);
    }


    /**
     * Updates the details of an existing club in the database.
     * This function is typically used to modify the information of a club based on its ID.
     *
     * @param id   The ID of the club to be updated.
     * @param club An object containing the updated details of the club.
     * @return A ResponseEntity containing a success message, HTTP status, and a response string indicating
     * the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateClub(@PathVariable("id") Long id, @RequestBody Club club) {
        String response = clubService.updateClub(id, club);
        return ResponseHandler.responseBuilder(CLUB_UPDATION, HttpStatus.OK, response);
    }


    /**
     * Deletes an existing club from the database.
     * This function is typically used to remove a club based on its ID.
     *
     * @param id The ID of the club to be deleted.
     * @return A ResponseEntity with a success message and HTTP status indicating the deletion result.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClub(@PathVariable("id") Long id) {
        String response = clubService.deleteClub(id);
        return ResponseHandler.responseBuilder(CLUB_DELETION, HttpStatus.NO_CONTENT, null);
    }
}
