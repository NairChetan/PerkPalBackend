package com.perkpal.service.impl;

import com.perkpal.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perkpal.dto.ParticipationDto;
import com.perkpal.entity.Participation;
import com.perkpal.exception.ResourceNotFoundException;
import com.perkpal.repository.ParticipationRepository;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipationServiceImpl implements ParticipationService {
    @Autowired
    private  ParticipationRepository participationRepository;
    @Autowired
    private  ModelMapper mapper;


    @Override
    public ParticipationDto createParticipation(ParticipationDto participationDto) {
        Participation participation = mapper.map(participationDto, Participation.class);
        Participation newParticipation = participationRepository.save(participation);
        return mapper.map(newParticipation, ParticipationDto.class);
    }

    @Override
    public ParticipationDto getParticipationById(Long id) {
        Participation participation = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation", "id", id));
        return mapper.map(participation, ParticipationDto.class);
    }

    @Override
    public List<ParticipationDto> getAllParticipations() {
        List<Participation> participations = participationRepository.findAll();
        return participations.stream()
                .map(participation -> mapper.map(participation, ParticipationDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public ParticipationDto updateParticipation(Long id, ParticipationDto participationDto) {
        Participation participation = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation", "id", id));

        mapper.map(participationDto, participation);

        Participation updatedParticipation = participationRepository.save(participation);
        return mapper.map(updatedParticipation, ParticipationDto.class);
    }

    @Override
    public void deleteParticipation(Long id) {
        Participation participation = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation", "id", id));
        participationRepository.delete(participation);
    }

}
