package com.perkpal.service;

import com.perkpal.dto.ClubDto;
import com.perkpal.entity.Club;


import java.util.List;

public interface ClubService {
    List<ClubDto> getClubs();
    ClubDto createClub(ClubDto clubDto);
    String updateClub(Long id, Club club);
    String deleteClub(Long id);
}
