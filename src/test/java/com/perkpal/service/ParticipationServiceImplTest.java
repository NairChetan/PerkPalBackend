package com.perkpal.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.perkpal.dto.ParticipationDto;
import com.perkpal.entity.Activity;
import com.perkpal.entity.Employee;
import com.perkpal.entity.Participation;
import com.perkpal.repository.ParticipationRepository;
import com.perkpal.service.impl.ParticipationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.util.Optional;

public class ParticipationServiceImplTest {

    @InjectMocks
    private ParticipationServiceImpl participationService;

    @Mock
    private ParticipationRepository participationRepository;

    @Mock
    private ModelMapper modelMapper;

    private Participation participation;
    private ParticipationDto participationDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock entities and DTOs
        participation = new Participation();
        participation.setActivityId(new Activity());
        participation.setEmployee(new Employee());
        participation.setDuration(60);
        participation.setDescription("Sample Description");
        participation.setCreatedBy(1L);

        participationDto = new ParticipationDto();
        participationDto.setActivityId(1L);
        participationDto.setEmployeeFirstName("John");
        participationDto.setEmployeeLastName("Doe");
        participationDto.setDuration(60);
        participationDto.setRemarks("Sample Description");
        participationDto.setParticipationDate(new Timestamp(System.currentTimeMillis()));
        participationDto.setApprovalDate(new Timestamp(System.currentTimeMillis()));
        participationDto.setApprovedByFirstName("Admin");
        participationDto.setApprovalStatus("pending");
    }

    @Test
    void testCreateParticipation() {
        when(modelMapper.map(participationDto, Participation.class)).thenReturn(participation);
        when(participationRepository.save(participation)).thenReturn(participation);
        when(modelMapper.map(participation, ParticipationDto.class)).thenReturn(participationDto);

        ParticipationDto result = participationService.createParticipation(participationDto);

        assertNotNull(result);
        assertEquals(participationDto.getActivityId(), result.getActivityId());
        assertEquals(participationDto.getEmployeeFirstName(), result.getEmployeeFirstName());
        verify(participationRepository, times(1)).save(any(Participation.class));
    }

    @Test
    void testGetParticipationById() {
        when(participationRepository.findById(1L)).thenReturn(Optional.of(participation));
        when(modelMapper.map(participation, ParticipationDto.class)).thenReturn(participationDto);

        ParticipationDto result = participationService.getParticipationById(1L);

        assertNotNull(result);
        assertEquals(participationDto.getActivityId(), result.getActivityId());
        assertEquals(participationDto.getEmployeeFirstName(), result.getEmployeeFirstName());
    }

    @Test
    void testUpdateParticipation() {
        ParticipationDto updatedDto = new ParticipationDto();
        updatedDto.setActivityId(2L);
        updatedDto.setEmployeeFirstName("Jane");
        updatedDto.setEmployeeLastName("Smith");
        updatedDto.setDuration(120);
        updatedDto.setRemarks("Updated Description");
        updatedDto.setParticipationDate(new Timestamp(System.currentTimeMillis()));
        updatedDto.setApprovalDate(new Timestamp(System.currentTimeMillis()));
        updatedDto.setApprovedByFirstName("Admin");
        updatedDto.setApprovalStatus("approved");

        when(participationRepository.findById(1L)).thenReturn(Optional.of(participation));
        when(modelMapper.map(updatedDto, Participation.class)).thenReturn(participation);
        when(participationRepository.save(participation)).thenReturn(participation);
        when(modelMapper.map(participation, ParticipationDto.class)).thenReturn(updatedDto);

        ParticipationDto result = participationService.updateParticipation(1L, updatedDto);

        assertNotNull(result);
        assertEquals(updatedDto.getActivityId(), result.getActivityId());
        assertEquals(updatedDto.getEmployeeFirstName(), result.getEmployeeFirstName());
        verify(participationRepository, times(1)).save(any(Participation.class));
    }

    @Test
    void testDeleteParticipation() {
        when(participationRepository.findById(1L)).thenReturn(Optional.of(participation));
        doNothing().when(participationRepository).delete(participation);

        participationService.deleteParticipation(1L);

        verify(participationRepository, times(1)).delete(participation);
    }
}
 