//package com.perkpal.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.perkpal.dto.*;
//import com.perkpal.repository.ActivityRepository;
//import com.perkpal.repository.EmployeeRepository;
//import com.perkpal.service.ParticipationService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.sql.Timestamp;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//@WebMvcTest(controllers = ParticipationController.class)
//public class ParticipationControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ActivityRepository activityRepository;
//
//    @MockBean
//    private EmployeeRepository employeeRepository;
//
//    @MockBean
//    private ParticipationService participationService;
//
//    @InjectMocks
//    private ParticipationController participationController;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(participationController).build();
//    }
//
//    @Test
//    /**
//     * Tests the creation of a new participation record with a valid ParticipationDto.
//     *
//     * This test ensures that when a valid ParticipationDto is sent in a POST request to create a participation,
//     * the controller returns a 201 Created status with the correct response body.
//     *
//     * @throws Exception If there is any error during the test execution.
//     */
//    public void givenValidParticipationDto_whenCreateParticipation_thenReturnsCreated() throws Exception {
//        // Given
//        ParticipationDto participationDto = new ParticipationDto();
//        participationDto.setActivityId(1L);
//        participationDto.setEmployeeFirstName("John");
//        participationDto.setEmployeeLastName("Doe");
//        participationDto.setDuration(120);
//        participationDto.setRemarks("Good performance");
//        participationDto.setParticipationDate(new Timestamp(System.currentTimeMillis()));
//        participationDto.setApprovalDate(new Timestamp(System.currentTimeMillis()));
//        participationDto.setApprovedByFirstName("Manager");
//        participationDto.setApprovalStatus("Approved");
//
//        when(participationService.createParticipation(any(ParticipationDto.class)))
//                .thenReturn(participationDto);
//
//        // When & Then
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/participation")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(participationDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Participation created successfully"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.activityId").value(participationDto.getActivityId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.employeeFirstName").value(participationDto.getEmployeeFirstName()));
//    }
//
//    @Test
//    /**
//     * Tests the retrieval of a participation record by its ID.
//     *
//     * This test ensures that when a valid participation ID is sent in a GET request,
//     * the controller returns a 200 OK status with the correct ParticipationDto in the response body.
//     *
//     * @throws Exception If there is any error during the test execution.
//     */
//    public void givenExistingParticipationId_whenGetParticipationById_thenReturnsParticipationDto() throws Exception {
//        // Given
//        Long participationId = 1L;
//        ParticipationDto participationDto = new ParticipationDto();
//        participationDto.setActivityId(1L);
//        participationDto.setEmployeeFirstName("John");
//        participationDto.setEmployeeLastName("Doe");
//        participationDto.setDuration(120);
//        participationDto.setRemarks("Good performance");
//        participationDto.setParticipationDate(new Timestamp(System.currentTimeMillis()));
//        participationDto.setApprovalDate(new Timestamp(System.currentTimeMillis()));
//        participationDto.setApprovedByFirstName("Manager");
//        participationDto.setApprovalStatus("Approved");
//
//        when(participationService.getParticipationById(participationId))
//                .thenReturn(participationDto);
//
//        // When & Then
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/participation/{id}", participationId)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Participation retrieved successfully"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.activityId").value(participationDto.getActivityId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.employeeFirstName").value(participationDto.getEmployeeFirstName()));
//    }
//
//    @Test
//    /**
//     * Tests the retrieval of all participation records.
//     *
//     * This test ensures that when a GET request is sent to fetch all participations,
//     * the controller returns a 200 OK status with a list of ParticipationDto objects in the response body.
//     *
//     * @throws Exception If there is any error during the test execution.
//     */
//    public void givenExistingParticipations_whenGetAllParticipations_thenReturnsListOfParticipationDto() throws Exception {
//        // Given
//        ParticipationDto participationDto1 = new ParticipationDto();
//        participationDto1.setActivityId(1L);
//        participationDto1.setEmployeeFirstName("John");
//        participationDto1.setEmployeeLastName("Doe");
//        participationDto1.setDuration(120);
//        participationDto1.setRemarks("Good performance");
//        participationDto1.setParticipationDate(new Timestamp(System.currentTimeMillis()));
//        participationDto1.setApprovalDate(new Timestamp(System.currentTimeMillis()));
//        participationDto1.setApprovedByFirstName("Manager");
//        participationDto1.setApprovalStatus("Approved");
//
//        ParticipationDto participationDto2 = new ParticipationDto();
//        participationDto2.setActivityId(2L);
//        participationDto2.setEmployeeFirstName("Jane");
//        participationDto2.setEmployeeLastName("Smith");
//        participationDto2.setDuration(90);
//        participationDto2.setRemarks("Excellent performance");
//        participationDto2.setParticipationDate(new Timestamp(System.currentTimeMillis()));
//        participationDto2.setApprovalDate(new Timestamp(System.currentTimeMillis()));
//        participationDto2.setApprovedByFirstName("Director");
//        participationDto2.setApprovalStatus("Approved");
//
//        List<ParticipationDto> participationDtoList = Arrays.asList(participationDto1, participationDto2);
//
//        when(participationService.getAllParticipations())
//                .thenReturn(participationDtoList);
//
//        // When & Then
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/participation")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Participation retrieved successfully"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].activityId").value(participationDto1.getActivityId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].employeeFirstName").value(participationDto2.getEmployeeFirstName()));
//    }
//
//    @Test
//    /**
//     * Tests the update of an existing participation record.
//     *
//     * This test ensures that when a valid participation ID and updated ParticipationDto are sent in a PUT request,
//     * the controller returns a 200 OK status with the updated ParticipationDto in the response body.
//     *
//     * @throws Exception If there is any error during the test execution.
//     */
//    public void givenExistingParticipationIdAndUpdatedDto_whenUpdateParticipation_thenReturnsUpdatedParticipationDto() throws Exception {
//        // Given
//        Long participationId = 1L;
//        ParticipationDto updatedParticipationDto = new ParticipationDto();
//        updatedParticipationDto.setActivityId(1L);
//        updatedParticipationDto.setEmployeeFirstName("John");
//        updatedParticipationDto.setEmployeeLastName("Doe");
//        updatedParticipationDto.setDuration(150); // Updated duration
//        updatedParticipationDto.setRemarks("Excellent performance"); // Updated remarks
//        updatedParticipationDto.setParticipationDate(new Timestamp(System.currentTimeMillis()));
//        updatedParticipationDto.setApprovalDate(new Timestamp(System.currentTimeMillis()));
//        updatedParticipationDto.setApprovedByFirstName("Manager");
//        updatedParticipationDto.setApprovalStatus("Approved");
//
//        ParticipationDto responseParticipationDto = new ParticipationDto();
//        responseParticipationDto.setActivityId(1L);
//        responseParticipationDto.setEmployeeFirstName("John");
//        responseParticipationDto.setEmployeeLastName("Doe");
//        responseParticipationDto.setDuration(150);
//        responseParticipationDto.setRemarks("Excellent performance");
//        responseParticipationDto.setParticipationDate(new Timestamp(System.currentTimeMillis()));
//        responseParticipationDto.setApprovalDate(new Timestamp(System.currentTimeMillis()));
//        responseParticipationDto.setApprovedByFirstName("Manager");
//        responseParticipationDto.setApprovalStatus("Approved");
//
//        when(participationService.updateParticipation(eq(participationId), any(ParticipationDto.class)))
//                .thenReturn(responseParticipationDto);
//
//        // When & Then
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/participation/{id}", participationId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedParticipationDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Participation updated successfully"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.duration").value(updatedParticipationDto.getDuration()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.remarks").value(updatedParticipationDto.getRemarks()));
//    }
//
//    @Test
//    /**
//     * Tests the deletion of a participation record by its ID.
//     *
//     * This test ensures that when a valid participation ID is sent in a DELETE request,
//     * the controller returns a 204 No Content status indicating successful deletion.
//     *
//     * @throws Exception If there is any error during the test execution.
//     */
//    public void givenExistingParticipationId_whenDeleteParticipation_thenReturnsNoContent() throws Exception {
//        // Given
//        Long participationId = 1L;
//        doNothing().when(participationService).deleteParticipation(participationId);
//
//        // When & Then
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/participation/{id}", participationId))
//                .andExpect(MockMvcResultMatchers.status().isNoContent());
//    }
//
//    @Test
//    /**
//     * Tests the creation of a new participation record with a ParticipationPostDto.
//     *
//     * This test ensures that when a valid ParticipationPostDto is sent in a POST request,
//     * the controller returns a 201 Created status with a success message.
//     *
//     * @throws Exception If there is any error during the test execution.
//     */
//    public void givenValidParticipationPostDto_whenCreateParticipation_thenReturnSuccessMessage() throws Exception {
//        // Arrange
//        ParticipationPostDto participationPostDto = new ParticipationPostDto();
//        participationPostDto.setCategoryName("E-Learning");
//        participationPostDto.setActivityName("E-Learning Certificate");
//        participationPostDto.setDescription("Completed the course");
//        participationPostDto.setDuration(60);
//        participationPostDto.setCreatedBy(1L);
//        participationPostDto.setEmployeeEmpId(2L);
//
//        // No need to mock participationService.createParticipation() as it returns void
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/participation/participationpost")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(participationPostDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Participation created successfully"));
//    }
//
//    @Test
//    /**
//     * Tests the retrieval of participations pending approval with pagination.
//     *
//     * This test ensures that when a GET request is made to fetch paginated pending participations,
//     * the controller returns a 200 OK status with the correct paginated response body.
//     *
//     * @throws Exception If there is any error during the test execution.
//     */
//    public void givenPendingParticipationsExist_whenGetParticipationForPendingApproval_thenReturnsPaginatedResponse() throws Exception {
//        // Given
//        int pageNumber = 0;
//        int pageSize = 4;
//        String sortBy = "participationDate";
//        String sortDir = "desc";
//
//        ParticipationDetailsFetchForPendingApprovalDto dto = new ParticipationDetailsFetchForPendingApprovalDto();
//        dto.setId(2990L);
//        dto.setEmployeeFirstName("Priya");
//        dto.setEmployeeLastName("Nair");
//        dto.setEmployeeId(2L);
//        dto.setActivityName("E-Learning Certificate");
//        dto.setActivityIdCategoryName("E-Learning");
//        dto.setParticipationDate(new Timestamp(System.currentTimeMillis()));
//        dto.setDuration(342);
//        dto.setDescription(null);
//
//        List<ParticipationDetailsFetchForPendingApprovalDto> dtoList = Collections.singletonList(dto);
//
//        PaginatedResponse<ParticipationDetailsFetchForPendingApprovalDto> paginatedResponse = new PaginatedResponse<>(
//                dtoList, 153, 610L, pageSize, pageNumber);
//
//        when(participationService.getAllPendingApproval(pageNumber, pageSize, sortBy, sortDir))
//                .thenReturn(paginatedResponse);
//
//        // When & Then
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/participation/pending-approval")
//                        .param("pageNumber", String.valueOf(pageNumber))
//                        .param("pageSize", String.valueOf(pageSize))
//                        .param("sortBy", sortBy)
//                        .param("sortDir", sortDir))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPages").value(153))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements").value(610))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size").value(pageSize))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.number").value(pageNumber))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].employeeFirstName").value("Priya"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].activityName").value("E-Learning Certificate"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Participation retrieved successfully"));
//    }
//
//    @Test
//    /**
//     * Tests the update of the approval status and remarks for a participation record.
//     *
//     * This test ensures that when a valid participation ID and ParticipationApprovalStatusRemarksPostDto are sent in a PUT request,
//     * the controller returns a 200 OK status with the updated approval status and remarks in the response body.
//     *
//     * @throws Exception If there is any error during the test execution.
//     */
//    public void givenValidIdAndDto_whenUpdateApprovalStatusAndRemark_thenReturnUpdatedParticipation() throws Exception {
//        // Arrange
//        Long id = 1L;
//        ParticipationApprovalStatusRemarksPostDto dto = new ParticipationApprovalStatusRemarksPostDto();
//        dto.setApprovalStatus("Approved");
//        dto.setRemarks("Well done");
//
//        ParticipationApprovalStatusRemarksPostDto updatedDto = new ParticipationApprovalStatusRemarksPostDto();
//        updatedDto.setApprovalStatus("Approved");
//        updatedDto.setRemarks("Well done");
//
//        when(participationService.updateApprovalStatusAndRemark(id, dto)).thenReturn(updatedDto);
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/participation/approval-status-remark/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Participation updated successfully"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.approvalStatus").value(updatedDto.getApprovalStatus()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.remarks").value(updatedDto.getRemarks()));
//    }
//}
//
