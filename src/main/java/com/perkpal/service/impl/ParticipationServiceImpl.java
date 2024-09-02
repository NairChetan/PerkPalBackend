package com.perkpal.service.impl;

import com.perkpal.dto.*;
import com.perkpal.entity.Activity;
import com.perkpal.entity.Employee;
import com.perkpal.entity.Participation;
import com.perkpal.exception.ResourceNotFoundException;
import com.perkpal.repository.ActivityRepository;
import com.perkpal.repository.EmployeeRepository;
import com.perkpal.repository.ParticipationRepository;
import com.perkpal.service.ParticipationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    /**
     * Creates a new participation record.
     *
     * This method maps the provided {@link ParticipationDto} to a {@link Participation} entity, saves it to the repository,
     * and returns the saved participation as a {@link ParticipationDto}.
     *
     * @param participationDto The {@link ParticipationDto} containing details of the participation to be created.
     * @return The created {@link ParticipationDto}.
     */
    public ParticipationDto createParticipation(ParticipationDto participationDto) {
        Participation participation = mapper.map(participationDto, Participation.class);
        Participation newParticipation = participationRepository.save(participation);
        return mapper.map(newParticipation, ParticipationDto.class);
    }

    @Override
    /**
     * Retrieves a participation record by its ID.
     *
     * This method finds a {@link Participation} by its ID and maps it to a {@link ParticipationDto}.
     * Throws a {@link ResourceNotFoundException} if the participation is not found.
     *
     * @param id The ID of the participation to be retrieved.
     * @return The {@link ParticipationDto} representing the participation.
     */
    public ParticipationDto getParticipationById(Long id) {
        Participation participation = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation", "id", id));
        return mapper.map(participation, ParticipationDto.class);
    }

    @Override
    /**
     * Retrieves all participation records.
     *
     * This method fetches all participations from the repository and maps each one to a {@link ParticipationDto}.
     *
     * @return A {@link List} of {@link ParticipationDto} representing all participations.
     */
    public List<ParticipationDto> getAllParticipations() {
        List<Participation> participations = participationRepository.findAll();
        return participations.stream()
                .map(participation -> mapper.map(participation, ParticipationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    /**
     * Updates an existing participation record.
     *
     * This method finds a {@link Participation} by its ID, updates it with the provided {@link ParticipationDto},
     * and saves the changes to the repository. Returns the updated participation as a {@link ParticipationDto}.
     * Throws a {@link ResourceNotFoundException} if the participation is not found.
     *
     * @param id The ID of the participation to be updated.
     * @param participationDto The {@link ParticipationDto} containing updated details.
     * @return The updated {@link ParticipationDto}.
     */
    public ParticipationDto updateParticipation(Long id, ParticipationDto participationDto) {
        Participation participation = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation", "id", id));

        mapper.map(participationDto, participation);
        Participation updatedParticipation = participationRepository.save(participation);
        return mapper.map(updatedParticipation, ParticipationDto.class);
    }

    @Override
    /**
     * Deletes a participation record by its ID.
     *
     * This method finds a {@link Participation} by its ID and deletes it from the repository.
     * Throws a {@link ResourceNotFoundException} if the participation is not found.
     *
     * @param id The ID of the participation to be deleted.
     */
    public void deleteParticipation(Long id) {
        Participation participation = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation", "id", id));
        participationRepository.delete(participation);
    }

    @Override
    /**
     * Retrieves all participations pending approval with pagination and sorting.
     *
     * This method retrieves all participations with a status of "pending" from the repository, paginates and sorts the results
     * according to the provided parameters, and returns a {@link PaginatedResponse} containing {@link ParticipationDetailsFetchForPendingApprovalDto}.
     *
     * @param pageNumber The page number for pagination.
     * @param pageSize The size of each page.
     * @param sortBy The field to sort by.
     * @param sortDir The direction to sort (ascending or descending).
     * @return A {@link PaginatedResponse} containing a list of {@link ParticipationDetailsFetchForPendingApprovalDto}.
     */
    public PaginatedResponse<ParticipationDetailsFetchForPendingApprovalDto> getAllPendingApproval(
            int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Participation> participations = participationRepository.findByApprovalStatus("pending", pageable);
        List<Participation> participationList = participations.getContent();

        return new PaginatedResponse<>(
                participationList.stream()
                        .map(participation -> mapper.map(participation, ParticipationDetailsFetchForPendingApprovalDto.class))
                        .collect(Collectors.toList()),
                participations.getTotalPages(),
                participations.getTotalElements(),
                participations.getSize(),
                participations.getNumber()
        );
    }

    @Override
    /**
     * Creates a new participation record using details from {@link ParticipationPostDto}.
     *
     * This method finds an activity by its category and name, finds an employee by its ID, creates a new {@link Participation} entity,
     * and saves it to the repository. Throws an {@link IllegalArgumentException} if the activity or employee is not found.
     *
     * @param participationPostDto The {@link ParticipationPostDto} containing details for the participation.
     */
    public void createParticipation(ParticipationPostDto participationPostDto) {
        // Find activities by category name
        List<Activity> activities = activityRepository.findByCategoryIdCategoryName(participationPostDto.getCategoryName());

        // Find the specific activity by activity name
        Optional<Activity> activityOptional = activities.stream()
                .filter(activity -> activity.getActivityName().equals(participationPostDto.getActivityName()))
                .findFirst();

        if (activityOptional.isEmpty()) {
            throw new IllegalArgumentException("Activity not found for the given category and name.");
        }

        // Find the employee by ID
        Optional<Employee> employeeOptional = employeeRepository.findById(participationPostDto.getEmployeeEmpId());
        if (employeeOptional.isEmpty()) {
            throw new IllegalArgumentException("Employee not found.");
        }

        Activity activity = activityOptional.get();
        Employee employee = employeeOptional.get();

        // Create a new Participation entity
        Participation participation = new Participation();
        participation.setActivityId(activity);
        participation.setDescription(participationPostDto.getDescription());
        participation.setDuration(participationPostDto.getDuration());
        participation.setCreatedBy(participationPostDto.getCreatedBy());
        participation.setEmployee(employee);

        // Save the participation record
        participationRepository.save(participation);
    }

    @Override
    /**
     * Updates the approval status and remarks of a participation record.
     *
     * This method updates the approval status and remarks of a {@link Participation} based on the provided
     * {@link ParticipationApprovalStatusRemarksPostDto}. The updated participation is saved to the repository
     * and returned as {@link ParticipationApprovalStatusRemarksPostDto}.
     * Throws a {@link ResourceNotFoundException} if the participation is not found.
     *
     * @param id The ID of the participation to be updated.
     * @param participationApprovalStatusRemarksPostDto The {@link ParticipationApprovalStatusRemarksPostDto} containing approval status and remarks.
     * @return The updated {@link ParticipationApprovalStatusRemarksPostDto}.
     */
    public ParticipationApprovalStatusRemarksPostDto updateApprovalStatusAndRemark(Long id, ParticipationApprovalStatusRemarksPostDto participationApprovalStatusRemarksPostDto) {
        Participation participation = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation", "id", id));
        mapper.map(participationApprovalStatusRemarksPostDto, participation);
        Participation updatedParticipation = participationRepository.save(participation);
        return mapper.map(updatedParticipation, ParticipationApprovalStatusRemarksPostDto.class);
    }



    @Override
    /**
     * Retrieves user logins by date and employee ID.
     *
     * This method finds all participations for a specific employee on a given date and maps them to
     * {@link ParticipationGetForUserLogDto}. The results are returned as a {@link List}.
     *
     * @param date The date of the participation.
     * @param employeeId The ID of the employee.
     * @return A {@link List} of {@link ParticipationGetForUserLogDto} representing user logins on the specified date.
     */
    public List<ParticipationGetForUserLogDto> getUserLoginsByDateAndEmployeeId(LocalDate date, Long employeeId) {
        List<Participation> participations = participationRepository.findByParticipationDateAndEmployeeId(date, employeeId);
        return participations.stream()
                .map(participation -> mapper.map(participation, ParticipationGetForUserLogDto.class))
                .collect(Collectors.toList());
    }

    @Override
    /**
     * Retrieves user logins by employee ID.
     *
     * This method finds all participations for a specific employee and maps them to
     * {@link ParticipationGetForUserLogDto}. The results are returned as a {@link List}.
     *
     * @param employeeId The ID of the employee.
     * @return A {@link List} of {@link ParticipationGetForUserLogDto} representing all user logins for the specified employee.
     */
    public List<ParticipationGetForUserLogDto> getUserLoginsByEmployeeId(Long employeeId) {
        List<Participation> participations = participationRepository.findByParticipationEmployeeId(employeeId);
        return participations.stream()
                .map(participation -> mapper.map(participation, ParticipationGetForUserLogDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PointsAccumulatedPerMonthDto> getApprovedPointsPerMonthForCurrentYear(Long employeeId) {
        return participationRepository.findApprovedPointsPerMonthForCurrentYear(employeeId);
    }

    @Override
    public PaginatedResponse<ParticipationDetailsFetchForPendingApprovalDto> searchParticipations(
            String activityName, String firstName, String lastName, Integer employeeId,
            int pageNumber, int pageSize, String sortBy, String sortDir) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<ParticipationDetailsFetchForPendingApprovalDto> pageResult = participationRepository.searchParticipation(
                activityName, firstName, lastName, employeeId, pageable);

        return new PaginatedResponse<>(pageResult.getContent(), pageResult.getTotalPages(), pageResult.getTotalElements(), pageResult.getSize(), pageResult.getNumber());
    }
}
