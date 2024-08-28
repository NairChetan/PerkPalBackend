package com.perkpal.repository;

import com.perkpal.dto.DuChartDataDto;
import com.perkpal.entity.Du;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface DuRepository extends JpaRepository<Du, Long> {

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
