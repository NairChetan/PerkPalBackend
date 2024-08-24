package com.perkpal.service;

import com.perkpal.dto.ParticipationDetailsFetchForPendingApprovalDto;
import com.perkpal.dto.ParticipationDto;

import java.util.List;

public interface ParticipationService {
    ParticipationDto createParticipation(ParticipationDto participationDto);

    ParticipationDto getParticipationById(Long id);

    List<ParticipationDto> getAllParticipations();

    ParticipationDto updateParticipation(Long id, ParticipationDto participationDto);

    void deleteParticipation(Long id);

    List<ParticipationDetailsFetchForPendingApprovalDto> getAllPendingApproval(int pageNumber,int pageSize);
}
