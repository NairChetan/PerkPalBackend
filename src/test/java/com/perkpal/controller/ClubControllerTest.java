package com.perkpal.controller;

import com.perkpal.dto.ClubDto;
import com.perkpal.entity.Club;
import com.perkpal.service.ClubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.perkpal.constants.Message.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClubControllerTest {

    @InjectMocks
    private ClubController clubController;

    @Mock
    private ClubService clubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /**
     * Test case for the GET /clubs endpoint.
     * Given: The service returns a list of ClubDto objects.
     * When: The controller's getClubs method is called.
     * Then: The response should contain the list of clubs with a success message and HTTP status 200 OK.
     */
    @Test
    void givenRequestForClubs_whenGetClubs_thenReturnsListOfClubs() {
        // Given
        List<ClubDto> clubs = Collections.singletonList(new ClubDto());
        when(clubService.getClubs()).thenReturn(clubs);

        // When
        ResponseEntity<Object> responseEntity = clubController.getClubs();

        // Then
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", REQUESTED_CLUB_DETAILS);
        expectedResponse.put("httpStatus", HttpStatus.OK);
        expectedResponse.put("data", clubs);

        assertEquals(expectedResponse, responseEntity.getBody());
    }


    /**
     * Test case for the POST /clubs endpoint.
     * Given: A valid ClubDto object is provided for creation.
     * When: The controller's createClub method is called with this object.
     * Then: The response should contain the created ClubDto with a success message and HTTP status 201 Created.
     */
    @Test
    void givenValidClubDto_whenCreateClub_thenReturnsCreatedClub() {
        // Given
        ClubDto clubDto = new ClubDto();
        when(clubService.createClub(any(ClubDto.class))).thenReturn(clubDto);

        // When
        ResponseEntity<Object> responseEntity = clubController.createClub(clubDto);

        // Then
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", CLUB_CREATION);
        expectedResponse.put("httpStatus", HttpStatus.CREATED);
        expectedResponse.put("data", clubDto);

        assertEquals(expectedResponse, responseEntity.getBody());
    }



    /**
     * Test case for the PUT /clubs/{id} endpoint.
     * Given: A valid Club object and ID are provided for updating.
     * When: The controller's updateClub method is called with these parameters.
     * Then: The response should contain the update confirmation message with HTTP status 200 OK.
     */
    @Test
    void givenValidClubAndId_whenUpdateClub_thenReturnsUpdateConfirmation() {
        // Given
        Club club = new Club();
        String responseMessage = "Updated Successfully";
        when(clubService.updateClub(any(Long.class), any(Club.class))).thenReturn(responseMessage);

        // When
        ResponseEntity<Object> responseEntity = clubController.updateClub(1L, club);

        // Then
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", CLUB_UPDATION);
        expectedResponse.put("httpStatus", HttpStatus.OK);
        expectedResponse.put("data", responseMessage);

        assertEquals(expectedResponse, responseEntity.getBody());
    }


    /**
     * Test case for the DELETE /clubs/{id} endpoint.
     * Given: A valid Club ID is provided for deletion.
     * When: The controller's deleteClub method is called with this ID.
     * Then: The response should contain the deletion confirmation message with HTTP status 204 No Content.
     */
    @Test
    void givenValidClubId_whenDeleteClub_thenReturnsDeletionConfirmation() {
        // Given
        String responseMessage = "Deleted Successfully";
        when(clubService.deleteClub(any(Long.class))).thenReturn(responseMessage);

        // When
        ResponseEntity<Object> responseEntity = clubController.deleteClub(1L);

        // Then
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", CLUB_DELETION);
        expectedResponse.put("httpStatus", HttpStatus.NO_CONTENT);
        expectedResponse.put("data", null);

        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
