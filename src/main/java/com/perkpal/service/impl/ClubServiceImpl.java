package com.perkpal.service.impl;

import com.perkpal.dto.ClubDto;
import com.perkpal.dto.EmployeeDto;
import com.perkpal.entity.Club;
import com.perkpal.entity.Employee;
import com.perkpal.repository.ActivityRepository;
import com.perkpal.repository.ClubRepository;
import com.perkpal.service.ClubService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ClubRepository clubRepository;


    /**
     * Retrieves a list of all clubs from the database.
     * This method fetches all club records, maps them to their corresponding DTOs,
     * and returns the list.
     *
     * @return A list of ClubDto objects representing all clubs in the database.
     */
    @Override
    public List<ClubDto> getClubs() {
        List<Club> clubList = clubRepository.findAll();
        return clubList.stream().map(club -> mapper.map(club, ClubDto.class)).collect(Collectors.toList());
    }


    /**
     * Creates a new club in the database.
     * This method maps the provided ClubDto to a Club entity, saves it to the database,
     * and then maps the saved entity back to a ClubDto to return.
     *
     * @param clubDto An object containing the details of the club to be created.
     * @return A ClubDto object representing the newly created club with the saved details.
     */
    @Override
    public ClubDto createClub(ClubDto clubDto) {
        Club club = mapper.map(clubDto, Club.class);
        Club newClub = clubRepository.save(club);
        ClubDto newClubDto = mapper.map(newClub, ClubDto.class);
        return newClubDto;

    }


    /**
     * Updates an existing club in the database.
     * This method checks if a club with the specified ID exists. If it does, it updates the club's details
     * and saves the updated entity to the database. It returns a success message upon successful update or
     * an error message if the club with the given ID is not found.
     *
     * @param id   The ID of the club to be updated.
     * @param club An object containing the updated details of the club.
     * @return A string message indicating the result of the update operation:
     * "Updated Successfully" if the update was successful, or "Club not found" if the club with the specified ID does not exist.
     */
    @Override
    public String updateClub(Long id, Club club) {
        if (clubRepository.existsById(id)) {
            club.setId(id);
            clubRepository.save(club);
            return "Updated Successfully";
        } else {
            return "Club not found";
        }

    }


    /**
     * Deletes an existing club from the database.
     * This method checks if a club with the specified ID exists. If it does, the club is deleted
     * from the database. The method returns a success message upon successful deletion or an error message
     * if the club with the given ID is not found.
     *
     * @param id The ID of the club to be deleted.
     * @return A string message indicating the result of the deletion operation:
     * "Deleted Successfully" if the deletion was successful, or "Club not found" if the club with the specified ID does not exist.
     */
    @Override
    public String deleteClub(Long id) {
        if (clubRepository.existsById(id)) {
            clubRepository.deleteById(id);
            return "Deleted Successfully";
        } else {
            return "Club not found";
        }

    }


}
