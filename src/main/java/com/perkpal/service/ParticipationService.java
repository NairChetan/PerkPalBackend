package com.perkpal.service;

import com.perkpal.dto.*;
import com.perkpal.entity.Participation;

import java.time.LocalDate;
import java.util.List;


public interface ParticipationService {
    ParticipationDto createParticipation(ParticipationDto participationDto);

    ParticipationDto getParticipationById(Long id);

    List<ParticipationDto> getAllParticipations();

    ParticipationDto updateParticipation(Long id, ParticipationDto participationDto);

    void deleteParticipation(Long id);

    PaginatedResponse<ParticipationDetailsFetchForPendingApprovalDto> getAllPendingApproval(int pageNo, int pageSize, String sortBy, String sortDir);

    void createParticipation(ParticipationPostDto participationPostDto);

//    List<ParticipationGetForUserLogDto> getUserLoginsByDate(LocalDate participationDate);

    List<ParticipationGetForUserLogDto> getUserLoginsByDateAndEmployeeId(LocalDate date, Long employeeId);

    List<ParticipationGetForUserLogDto> getUserLoginsByEmployeeId( Long employeeId);


    ParticipationApprovalStatusRemarksPostDto updateApprovalStatusAndRemark(Long id,ParticipationApprovalStatusRemarksPostDto participationApprovalStatusRemarksPostDto);

    List<PointsAccumulatedPerMonthDto> getApprovedPointsPerMonthForCurrentYear(Long employeeId);
    Participation updateParticipation(Long id, ParticipationPutForUserLogDto participationPutForUserLogDto);

    PaginatedResponse<ParticipationDetailsFetchForPendingApprovalDto> searchParticipations(
            String activityName, String firstName, String lastName, Integer employeeId, String approvalStatus, String participationDate, String approvalDate,
            int pageNumber, int pageSize, String sortBy, String sortDir);
}
