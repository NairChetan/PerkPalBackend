package com.perkpal.service;

import com.perkpal.dto.EmployeeDto;
import com.perkpal.dto.EmployeeUpdatePointsDto;
import com.perkpal.entity.Club;
import com.perkpal.entity.Du;
import com.perkpal.entity.Employee;
import com.perkpal.entity.Role;
import com.perkpal.repository.EmployeeRepository;
import com.perkpal.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private ModelMapper mapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test methods will go here
    @Test
    void testGetEmployees() {
        // Arrange
        // Creating related entities
        Du du = new Du();
        du.setId(1L);
        du.setDepartmentName("Finance Department");

        Role role = new Role();
        role.setId(1L);
        role.setRoleName("Manager");

        Club club = new Club();
        club.setId(1L);
        club.setClubName("Chess Club");

        // Initializing Employee entity for employee1
        Employee employee1 = new Employee();
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setDesignation("Senior Developer");
        employee1.setEmail("john.doe@example.com");
        employee1.setManager(true);
        employee1.setDuId(du);
        employee1.setRoleId(role);
        employee1.setTotalPoints(150L);
        employee1.setRedeemablePoints(75L);
        employee1.setClubId(club);
        employee1.setPhotoUrl("http://example.com/photo1.jpg");
        employee1.setLastLogin(Timestamp.valueOf("2023-08-20 10:10:10"));

        // Initializing Employee entity for employee2
        Employee employee2 = new Employee();
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setDesignation("Product Manager");
        employee2.setEmail("jane.smith@example.com");
        employee2.setManager(false);
        employee2.setDuId(du);
        employee2.setRoleId(role);
        employee2.setTotalPoints(200L);
        employee2.setRedeemablePoints(100L);
        employee2.setClubId(club);
        employee2.setPhotoUrl("http://example.com/photo2.jpg");
        employee2.setLastLogin(Timestamp.valueOf("2023-08-21 11:11:11"));

        // Initializing EmployeeDto for employeeDto1
        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setFirstName("John");
        employeeDto1.setLastName("Doe");
        employeeDto1.setDesignation("Senior Developer");
        employeeDto1.setEmail("john.doe@example.com");
        employeeDto1.setDuDepartmentName("Finance Department");
        employeeDto1.setRoleRoleName("Manager");
        employeeDto1.setTotalPoints(150L);
        employeeDto1.setRedeemablePoints(75L);
        employeeDto1.setClubClubName("Chess Club");
        employeeDto1.setPhotoUrl("http://example.com/photo1.jpg");

        // Initializing EmployeeDto for employeeDto2
        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto2.setFirstName("Jane");
        employeeDto2.setLastName("Smith");
        employeeDto2.setDesignation("Product Manager");
        employeeDto2.setEmail("jane.smith@example.com");
        employeeDto2.setDuDepartmentName("Finance Department");
        employeeDto2.setRoleRoleName("Manager");
        employeeDto2.setTotalPoints(200L);
        employeeDto2.setRedeemablePoints(100L);
        employeeDto2.setClubClubName("Chess Club");
        employeeDto2.setPhotoUrl("http://example.com/photo2.jpg");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
        when(mapper.map(employee1, EmployeeDto.class)).thenReturn(employeeDto1);
        when(mapper.map(employee2, EmployeeDto.class)).thenReturn(employeeDto2);

        // Act
        List<EmployeeDto> result = employeeService.getEmployees();

        // Assert
        assertEquals(2, result.size());
        assertEquals(employeeDto1, result.get(0));
        assertEquals(employeeDto2, result.get(1));
        verify(employeeRepository, times(1)).findAll();
    }
    @Test
    public void givenEmployeeExists_whenGetEmployeeById_thenReturnEmployeeDto() {
        Long employeeId = 1L;
        Employee employee = new Employee(); // Initialize as needed
        EmployeeDto employeeDto = new EmployeeDto(); // Initialize as needed

        // Given
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));
        given(mapper.map(employee, EmployeeDto.class)).willReturn(employeeDto);

        // When
        EmployeeDto result = employeeService.getEmployeeById(employeeId);

        // Then
        then(result).isNotNull(); // Verify the result is not null
        then(result).isEqualTo(employeeDto); // Verify the result matches the expected EmployeeDto
    }

    @Test
    public void givenEmployeeDoesNotExist_whenGetEmployeeById_thenReturnNull() {
        Long employeeId = 1L;

        // Given
        given(employeeRepository.findById(employeeId)).willReturn(Optional.empty());

        // When
        EmployeeDto result = employeeService.getEmployeeById(employeeId);

        // Then
        then(result).isNull(); // Verify the result is null when no employee is found
    }
    @Test
    public void givenEmployeeExists_whenUpdateEmployeePoints_thenUpdatePoints() {
        Long employeeId = 1L;
        Employee existingEmployee = new Employee();
        existingEmployee.setTotalPoints(100L);
        existingEmployee.setRedeemablePoints(50L);

        EmployeeUpdatePointsDto updateDto = new EmployeeUpdatePointsDto();
        updateDto.setTotalPoints(150L);
        updateDto.setRedeemablePoints(75L);

        Employee updatedEmployee = new Employee();
        updatedEmployee.setTotalPoints(150L);
        updatedEmployee.setRedeemablePoints(75L);

        // Given
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(existingEmployee));
        given(employeeRepository.save(existingEmployee)).willReturn(updatedEmployee);

        // When
        Employee result = employeeService.updateEmployeePoints(employeeId, updateDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotalPoints()).isEqualTo(150);
        assertThat(result.getRedeemablePoints()).isEqualTo(75);
    }

    @Test
    public void givenEmployeeDoesNotExist_whenUpdateEmployeePoints_thenThrowException() {
        Long employeeId = 1L;
        EmployeeUpdatePointsDto updateDto = new EmployeeUpdatePointsDto();
        updateDto.setTotalPoints(150L);
        updateDto.setRedeemablePoints(75L);

        // Given
        given(employeeRepository.findById(employeeId)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> employeeService.updateEmployeePoints(employeeId, updateDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee not found");
    }


}


