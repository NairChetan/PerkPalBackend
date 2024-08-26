package com.perkpal.repository;

import com.perkpal.dto.EmployeeLeaderBoardDto;
import com.perkpal.entity.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    Page<Participation> findByApprovalStatus(String approvalStatus, Pageable pageable);

    @Query("SELECT p.employee.id, SUM(p.duration) " +
            "FROM Participation p " +
            "WHERE p.approvalStatus = 'approved' " +
            "AND p.participationDate BETWEEN :initialDate AND :endDate " +
            "GROUP BY p.employee.id")
    List<Object[]> findEmployeePointsInDateRange(@Param("initialDate") Timestamp initialDate,
                                                 @Param("endDate") Timestamp endDate);


    @Query("SELECT new com.perkpal.dto.EmployeeLeaderBoardDto(" +
            "e.id, " +
            "CONCAT(e.firstName, ' ', e.lastName), " +
            "d.departmentName, " +
            "e.photoUrl, " +
            "(SUM(a.weightagePerHour * (p.duration / 60.0)))) " +  // Convert minutes to hours
            "FROM Participation p " +
            "JOIN p.activityId a " +
            "JOIN p.employee e " +
            "JOIN e.duId d " +
            "WHERE EXTRACT(YEAR FROM p.participationDate) = :year " +
            "AND p.approvalStatus = 'approved' " +
            "GROUP BY e.id, e.firstName, e.lastName, d.departmentName, e.photoUrl " +
            "ORDER BY SUM(a.weightagePerHour * (p.duration / 60.0)) DESC")  // Ordering by totalPoints
    List<EmployeeLeaderBoardDto> findEmployeeLeaderboardByYear(@Param("year") int year);
}
