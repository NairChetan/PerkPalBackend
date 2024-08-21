package com.perkpal.controller;

import com.perkpal.dto.ParticipationDto;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/participation")
public class ParticipationController {

    private final ParticipationService participationService;

    @Autowired
    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping
    public ResponseEntity<Object> createParticipation(@RequestBody ParticipationDto participationDto) {
        ParticipationDto newParticipation = participationService.createParticipation(participationDto);
        return ResponseHandler.responseBuilder("Participation created successfully", HttpStatus.CREATED, newParticipation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getParticipationById(@PathVariable Long id) {
        ParticipationDto participation = participationService.getParticipationById(id);
        return ResponseHandler.responseBuilder("Participation retrieved successfully", HttpStatus.OK, participation);
    }

    @GetMapping
    public ResponseEntity<Object> getAllParticipations() {
        List<ParticipationDto> participations = participationService.getAllParticipations();
        return ResponseHandler.responseBuilder("Participations retrieved successfully", HttpStatus.OK, participations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParticipation(@PathVariable Long id, @RequestBody ParticipationDto participationDto) {
        ParticipationDto updatedParticipation = participationService.updateParticipation(id, participationDto);
        return ResponseHandler.responseBuilder("Participation updated successfully", HttpStatus.OK, updatedParticipation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParticipation(@PathVariable Long id) {
        participationService.deleteParticipation(id);
        return ResponseHandler.responseBuilder("Participation deleted successfully", HttpStatus.NO_CONTENT, null);
    }
}
