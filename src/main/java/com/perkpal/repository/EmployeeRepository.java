package com.perkpal.repository;

import com.perkpal.dto.EmployeeSummaryDto;
import com.perkpal.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmail(String email);


    @Query("SELECT new com.perkpal.dto.EmployeeSummaryDto(e.id, e.firstName, e.lastName, e.duId.departmentName, " +
            "SUM((p.duration / 60.0) * a.weightagePerHour), e.photoUrl) " +
            "FROM Employee e " +
            "JOIN e.participation p " +
            "JOIN p.activityId a " +
            "WHERE p.approvalStatus = 'approved' " +
            "AND p.participationDate BETWEEN :initialDate AND :endDate " +
            "GROUP BY e.id, e.firstName, e.lastName, e.duId.departmentName, e.photoUrl " +
            "ORDER BY SUM((p.duration / 60.0) * a.weightagePerHour) DESC")
    List<EmployeeSummaryDto> findEmployeesByPointsInDateRange(@Param("initialDate") Timestamp initialDate,
                                                              @Param("endDate") Timestamp endDate);
}
