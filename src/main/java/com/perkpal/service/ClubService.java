package com.perkpal.service;

import com.perkpal.dto.ClubDto;


import java.util.List;

public interface ClubService {
    List<ClubDto> getClubs();
    ClubDto createClub(ClubDto clubDto);
}
