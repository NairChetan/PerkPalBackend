package com.perkpal.service;

import com.perkpal.dto.DuChartDataDto;
import com.perkpal.repository.DuRepository;
import com.perkpal.service.impl.DuServiceImpl; // Import DuServiceImpl from the correct package
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DuServiceImplTest {

    @Mock
    private DuRepository duRepository;

    @InjectMocks
    private DuServiceImpl duServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenValidDateRange_whenGetDuPointsInDateRange_thenReturnListOfDuChartDataDto() {
        // Given
        Timestamp startDate = Timestamp.valueOf("2024-01-01 00:00:00");
        Timestamp endDate = Timestamp.valueOf("2024-01-31 23:59:59");

        DuChartDataDto dto = new DuChartDataDto("Department A", 120.0);
        // Replace with actual constructor or setter usage

        List<DuChartDataDto> expectedData = new ArrayList<>();
        expectedData.add(dto);

        // When
        when(duRepository.findDuPointsInDateRange(startDate, endDate)).thenReturn(expectedData);
        List<DuChartDataDto> actualData = duServiceImpl.getDuPointsInDateRange(startDate, endDate);

        // Then
        assertEquals(expectedData, actualData, "The returned data should match the expected data.");
    }
}
