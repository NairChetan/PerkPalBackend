package com.perkpal.service.impl;

import com.perkpal.dto.ParticipationDetailsFetchForPendingApprovalDto;
import com.perkpal.dto.ParticipationDto;
import com.perkpal.dto.ParticipationPostDto;
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
    ActivityRepository activityRepository;

    @Autowired
    EmployeeRepository employeeRepository;


    @Override
    public ParticipationDto createParticipation(ParticipationDto participationDto) {
        Participation participation = mapper.map(participationDto, Participation.class);
        Participation newParticipation = participationRepository.save(participation);
        return mapper.map(newParticipation, ParticipationDto.class);
    }

    @Override
    public ParticipationDto getParticipationById(Long id) {
        Participation participation = participationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Participation", "id", id));
        return mapper.map(participation, ParticipationDto.class);
    }

    @Override
    public List<ParticipationDto> getAllParticipations() {
        List<Participation> participations = participationRepository.findAll();
        return participations.stream().map(participation -> mapper.map(participation, ParticipationDto.class)).collect(Collectors.toList());
    }

    @Override
    public ParticipationDto updateParticipation(Long id, ParticipationDto participationDto) {
        Participation participation = participationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Participation", "id", id));

        mapper.map(participationDto, participation);

        Participation updatedParticipation = participationRepository.save(participation);
        return mapper.map(updatedParticipation, ParticipationDto.class);
    }

    @Override
    public void deleteParticipation(Long id) {
        Participation participation = participationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Participation", "id", id));
        participationRepository.delete(participation);
    }

    @Override
    public List<ParticipationDetailsFetchForPendingApprovalDto> getAllPendingApproval(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Participation> participations = participationRepository.findByApprovalStatus("pending",pageable);
        List<Participation> participationList = participations.getContent();
        return participationList.stream().map(participation -> mapper.map(participation, ParticipationDetailsFetchForPendingApprovalDto.class)).collect(Collectors.toList());
    }

    @Override
    public void createParticipation(ParticipationPostDto participationPostDto) {
        // Find activities by category name
        List<Activity> activities = activityRepository.findByCategoryIdCategoryName(participationPostDto.getCategoryName());

        // Find the specific activity by activity name
        Optional<Activity> activityOptional = activities.stream().filter(activity -> activity.getActivityName().equals(participationPostDto.getActivityName())).findFirst();

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
        participation.setRemarks(participationPostDto.getDescription());
        participation.setDuration(participationPostDto.getDuration());
        participation.setCreatedBy(participationPostDto.getCreatedBy());
        participation.setEmployee(employee);


        // Save the participation record
        participationRepository.save(participation);


    }
/*    @Override
    public List<ParticipationDetailsFetchForPendingApprovalDto> getAllPendingApproval(int pageNumber, int pageSize) {
        List<Participation> participationsByApprovalStatus = participationRepository.findByApprovalStatus("pending");
        return participationsByApprovalStatus.stream().map(participation -> mapper.map(participation, ParticipationDetailsFetchForPendingApprovalDto.class)).collect(Collectors.toList());
    }*/
}
