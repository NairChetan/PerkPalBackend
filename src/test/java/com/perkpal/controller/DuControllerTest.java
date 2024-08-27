package com.perkpal.controller;

import com.perkpal.dto.DuChartDataDto;
import com.perkpal.service.DuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DuControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private DuController duController;

    @Mock
    private DuService duService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(duController).build();
    }

    @Test
    @DisplayName("Test getDuPointsInDateRange - Success")
    void givenValidDateRange_whenGetDuPointsInDateRange_thenReturnsDuChartData() throws Exception {
        // Given: Mocked data and service behavior
        LocalDateTime startDateTime = LocalDateTime.of(2024, 8, 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 8, 31, 23, 59);
        Timestamp startDate = Timestamp.valueOf(startDateTime);
        Timestamp endDate = Timestamp.valueOf(endDateTime);

        List<DuChartDataDto> duChartData = Arrays.asList(
                new DuChartDataDto("IT", 1500.0),
                new DuChartDataDto("HR", 1200.0)
        );

        when(duService.getDuPointsInDateRange(startDate, endDate)).thenReturn(duChartData);

        // When: Simulate the GET request
        mockMvc.perform(get("/api/v1/du/points")
                        .param("startDate", startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .param("endDate", endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"duName\":\"IT\",\"totalPoints\":1500.0},{\"duName\":\"HR\",\"totalPoints\":1200.0}]"))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()));

        // Verify service interaction
        verify(duService, times(1)).getDuPointsInDateRange(startDate, endDate);
    }


}
