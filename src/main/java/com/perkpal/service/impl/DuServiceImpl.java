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

    /**
     * Retrieves DU points data within a specified date range.
     *
     * @param startDate The start date (inclusive) of the date range for retrieving DU points.
     *                  This is a Timestamp object representing the lower bound of the range.
     * @param endDate   The end date (inclusive) of the date range for retrieving DU points.
     *                  This is a Timestamp object representing the upper bound of the range.
     * @return A list of DuChartDataDto objects containing DU points data within the specified date range.
     * Each object in the list represents a set of DU points for a particular time period.
     * @throws IllegalArgumentException if the startDate or endDate is null, or if the startDate is after the endDate.
     */
    @Override
    public List<DuChartDataDto> getDuPointsInDateRange(Timestamp startDate, Timestamp endDate) {
        return duRepository.findDuPointsInDateRange(startDate, endDate);
    }
}
