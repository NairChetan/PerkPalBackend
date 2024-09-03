package com.perkpal.repository;

import com.perkpal.dto.DuChartDataDto;
import com.perkpal.entity.Du;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface DuRepository extends JpaRepository<Du, Long> {


    /**
     * Retrieves DU points within a specified date range, grouped by department name.
     * <p>
     * This query calculates the DU points based on the duration of participation in activities,
     * the weightage per hour of those activities, and filters the results by the participation
     * date range and approval status.
     *
     * @param startDate The start date of the date range for filtering participation records (inclusive).
     * @param endDate   The end date of the date range for filtering participation records (inclusive).
     * @return A list of {@link DuChartDataDto} objects containing the department names and their corresponding DU points within the specified date range.
     */
    @Query("SELECT new com.perkpal.dto.DuChartDataDto(d.departmentName, " +
            "SUM((p.duration / 60.0) * a.weightagePerHour)) " +
            "FROM Du d " +
            "JOIN d.employees e " +
            "JOIN e.participation p " +
            "JOIN p.activityId a " +
            "WHERE p.participationDate BETWEEN :startDate AND :endDate " +
            "AND p.approvalStatus = 'approved' " +
            "GROUP BY d.departmentName")
    List<DuChartDataDto> findDuPointsInDateRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
