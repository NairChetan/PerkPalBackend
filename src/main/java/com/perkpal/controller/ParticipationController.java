package com.perkpal.controller;
import com.perkpal.dto.ParticipationDto;
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
    public ResponseEntity<ParticipationDto> createParticipation(@RequestBody ParticipationDto participationDto) {
        ParticipationDto newParticipation = participationService.createParticipation(participationDto);
        return new ResponseEntity<>(newParticipation, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipationDto> getParticipationById(@PathVariable Long id) {
        ParticipationDto participation = participationService.getParticipationById(id);
        return ResponseEntity.ok(participation);
    }

    @GetMapping
    public ResponseEntity<List<ParticipationDto>> getAllParticipations() {
        List<ParticipationDto> participations = participationService.getAllParticipations();
        return ResponseEntity.ok(participations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipationDto> updateParticipation(@PathVariable Long id, @RequestBody ParticipationDto participationDto) {
        ParticipationDto updatedParticipation = participationService.updateParticipation(id, participationDto);
        return ResponseEntity.ok(updatedParticipation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipation(@PathVariable Long id) {
        participationService.deleteParticipation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

