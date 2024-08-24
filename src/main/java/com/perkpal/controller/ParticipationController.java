package com.perkpal.controller;

import com.perkpal.dto.ParticipationDto;
import com.perkpal.dto.ParticipationPostDto;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(value = PAGE_NUMBER,defaultValue = DEFAULT_PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = PAGE_SIZE,defaultValue = DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = SORT_BY,defaultValue = DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = SORT_DIRECTION,defaultValue = DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        return ResponseHandler.responseBuilder(PARTICIPATION_RETRIEVAL,HttpStatus.OK,participationService.getAllPendingApproval(pageNumber,pageSize,sortBy,sortDir));
    }
}
