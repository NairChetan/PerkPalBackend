package com.perkpal.repository;


import com.perkpal.dto.ParticipationGetForUserLogDto;
import com.perkpal.entity.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    Page<Participation> findByApprovalStatus(String approvalStatus, Pageable pageable);

//
//    @Query(value = "SELECT * FROM participation WHERE DATE(participation_date) = :date", nativeQuery = true)
//    List<Participation> findByParticipationDate(@Param("date") LocalDate date);

    @Query(value = "SELECT * FROM participation WHERE DATE(participation_date) = :date AND emp_id = :employeeId", nativeQuery = true)
    List<Participation> findByParticipationDateAndEmployeeId(
            @Param("date") LocalDate date,
            @Param("employeeId") Long employeeId);

    @Query(value = "SELECT * FROM participation WHERE emp_id = :employeeId", nativeQuery = true)
    List<Participation> findByParticipationEmployeeId(
            @Param("employeeId") Long employeeId);

}

