package com.perkpal.service;

import com.perkpal.dto.*;

import java.util.List;

public interface ParticipationService {
    ParticipationDto createParticipation(ParticipationDto participationDto);

    ParticipationDto getParticipationById(Long id);

    List<ParticipationDto> getAllParticipations();

    ParticipationDto updateParticipation(Long id, ParticipationDto participationDto);

    void deleteParticipation(Long id);

    PaginatedResponse<ParticipationDetailsFetchForPendingApprovalDto> getAllPendingApproval(int pageNo, int pageSize, String sortBy, String sortDir);

    void createParticipation(ParticipationPostDto participationPostDto);

    ParticipationApprovalStatusRemarksPostDto updateApprovalStatusAndRemark(Long id,ParticipationApprovalStatusRemarksPostDto participationApprovalStatusRemarksPostDto);

}
