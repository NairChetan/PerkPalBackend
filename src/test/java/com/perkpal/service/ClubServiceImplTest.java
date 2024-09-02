package com.perkpal.service;

import com.perkpal.dto.ClubDto;
import com.perkpal.entity.Club;
import com.perkpal.repository.ClubRepository;
import com.perkpal.service.impl.ClubServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ClubServiceImplTest {

    @Mock
    private ModelMapper mapper;

    @Mock
    private ClubRepository clubRepository;

    @InjectMocks
    private ClubServiceImpl clubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for the `getClubs` method in `ClubServiceImpl` to ensure it returns a list of ClubDto objects
     * when there are clubs in the repository. This test verifies that the service method correctly interacts
     * with the repository and mapper to produce the expected result.
     */
    @Test
    void testGetClubs() {
        // Arrange
        Club club1 = new Club();
        club1.setClubName("Chess Club");
        club1.setClubDescription("A club for chess enthusiasts");
        club1.setInitialThreshold(100L);
        club1.setFinalThreshold(200L);

        Club club2 = new Club();
        club2.setClubName("Football Club");
        club2.setClubDescription("A club for football players");
        club2.setInitialThreshold(150L);
        club2.setFinalThreshold(250L);

        ClubDto clubDto1 = new ClubDto(1, 1L, "Chess Club", "A club for chess enthusiasts", 100L, 200L);
        ClubDto clubDto2 = new ClubDto(2, 1L, "Football Club", "A club for football players", 150L, 250L);

        when(clubRepository.findAll()).thenReturn(Arrays.asList(club1, club2));
        when(mapper.map(club1, ClubDto.class)).thenReturn(clubDto1);
        when(mapper.map(club2, ClubDto.class)).thenReturn(clubDto2);

        // Act
        List<ClubDto> result = clubService.getClubs();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(clubDto1);
        assertThat(result.get(1)).isEqualTo(clubDto2);
        verify(clubRepository, times(1)).findAll();
    }

    /**
     * Test for the `createClub` method in `ClubServiceImpl` to ensure it correctly saves a new club to the repository.
     * This test verifies that the service method maps the DTO to the entity, saves the entity, and maps it back to a DTO.
     */
    @Test
    void testCreateClub() {
        // Arrange
        ClubDto clubDto = new ClubDto(1, 1L, "Chess Club", "A club for chess enthusiasts", 100L, 200L);
        Club club = new Club();
        club.setClubName("Chess Club");
        club.setClubDescription("A club for chess enthusiasts");
        club.setInitialThreshold(100L);
        club.setFinalThreshold(200L);

        when(mapper.map(clubDto, Club.class)).thenReturn(club);
        when(clubRepository.save(club)).thenReturn(club);
        when(mapper.map(club, ClubDto.class)).thenReturn(clubDto);

        // Act
        ClubDto result = clubService.createClub(clubDto);

        // Assert
        assertThat(result).isEqualTo(clubDto);
        verify(clubRepository, times(1)).save(club);
    }

    /**
     * Test for the `updateClub` method in `ClubServiceImpl` to ensure it correctly updates an existing club.
     * This test verifies that the service updates the club when it exists and returns a success message.
     */
    @Test
    void testUpdateClub_ExistingClub() {
        // Arrange
        Long clubId = 1L;
        Club existingClub = new Club();
        existingClub.setId(clubId);
        existingClub.setClubName("Chess Club");
        existingClub.setClubDescription("A club for chess enthusiasts");
        existingClub.setInitialThreshold(100L);
        existingClub.setFinalThreshold(200L);

        when(clubRepository.existsById(clubId)).thenReturn(true);
        when(clubRepository.save(existingClub)).thenReturn(existingClub);

        // Act
        String result = clubService.updateClub(clubId, existingClub);

        // Assert
        assertThat(result).isEqualTo("Updated Successfully");
        verify(clubRepository, times(1)).existsById(clubId);
        verify(clubRepository, times(1)).save(existingClub);
    }

    /**
     * Test for the `updateClub` method in `ClubServiceImpl` to ensure it returns an error message
     * when trying to update a non-existent club.
     */
    @Test
    void testUpdateClub_NonExistentClub() {
        // Arrange
        Long clubId = 1L;
        Club updatedClub = new Club();
        updatedClub.setId(clubId);
        updatedClub.setClubName("Updated Club");
        updatedClub.setClubDescription("Updated Description");
        updatedClub.setInitialThreshold(150L);
        updatedClub.setFinalThreshold(300L);

        when(clubRepository.existsById(clubId)).thenReturn(false);

        // Act
        String result = clubService.updateClub(clubId, updatedClub);

        // Assert
        assertThat(result).isEqualTo("Club not found");
        verify(clubRepository, times(1)).existsById(clubId);
        verify(clubRepository, times(0)).save(updatedClub);
    }

    /**
     * Test for the `deleteClub` method in `ClubServiceImpl` to ensure it correctly deletes an existing club.
     * This test verifies that the service deletes the club when it exists and returns a success message.
     */
    @Test
    void testDeleteClub_ExistingClub() {
        // Arrange
        Long clubId = 1L;

        when(clubRepository.existsById(clubId)).thenReturn(true);
        doNothing().when(clubRepository).deleteById(clubId);

        // Act
        String result = clubService.deleteClub(clubId);

        // Assert
        assertThat(result).isEqualTo("Deleted Successfully");
        verify(clubRepository, times(1)).existsById(clubId);
        verify(clubRepository, times(1)).deleteById(clubId);
    }

    /**
     * Test for the `deleteClub` method in `ClubServiceImpl` to ensure it returns an error message
     * when trying to delete a non-existent club.
     */
    @Test
    void testDeleteClub_NonExistentClub() {
        // Arrange
        Long clubId = 1L;

        when(clubRepository.existsById(clubId)).thenReturn(false);

        // Act
        String result = clubService.deleteClub(clubId);

        // Assert
        assertThat(result).isEqualTo("Club not found");
        verify(clubRepository, times(1)).existsById(clubId);
        verify(clubRepository, times(0)).deleteById(clubId);
    }
}
