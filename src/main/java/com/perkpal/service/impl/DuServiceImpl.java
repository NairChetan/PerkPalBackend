package com.perkpal.service.impl;

import com.perkpal.dto.DuChartDataDto;
import com.perkpal.repository.DuRepository;
import com.perkpal.service.DuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DuServiceImpl implements DuService {

    @Autowired
    private DuRepository duRepository;

    @Override
    public List<DuChartDataDto> getDuPointsInDateRange(Timestamp startDate, Timestamp endDate) {
        return duRepository.findDuPointsInDateRange(startDate, endDate);
    }
}
