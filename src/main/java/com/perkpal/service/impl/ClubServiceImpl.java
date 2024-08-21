package com.perkpal.service.impl;

import com.perkpal.dto.ClubDto;
import com.perkpal.dto.EmployeeDto;
import com.perkpal.entity.Club;
import com.perkpal.entity.Employee;
import com.perkpal.repository.ActivityRepository;
import com.perkpal.repository.ClubRepository;
import com.perkpal.service.ClubService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ClubRepository clubRepository;

    @Override
    public List<ClubDto> getClubs() {
        List<Club> clubList = clubRepository.findAll();
        return clubList.stream().map(club -> mapper.map(club, ClubDto.class)).collect(Collectors.toList());
    }

    @Override
    public ClubDto createClub(ClubDto clubDto) {
        Club club = mapper.map(clubDto,Club.class);
        Club newClub = clubRepository.save(club);
        ClubDto newClubDto = mapper.map(newClub,ClubDto.class);
        return newClubDto;

    }


}
