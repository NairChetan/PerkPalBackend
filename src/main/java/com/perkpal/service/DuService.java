package com.perkpal.service;

import com.perkpal.dto.DuChartDataDto;

import java.sql.Timestamp;
import java.util.List;

public interface DuService {
    List<DuChartDataDto> getDuPointsInDateRange(Timestamp startDate, Timestamp endDate);
}
