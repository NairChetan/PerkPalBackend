package com.perkpal.repository;

import com.perkpal.dto.EmployeeActivitySummaryDto;
import com.perkpal.dto.EmployeeSummaryDto;
import com.perkpal.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Finds an employee by their email address.
     *
     * @param email The email address of the employee to find.
     * @return An {@link Optional} containing the found {@link Employee}, or {@link Optional#empty()} if no employee was found.
     */
    Optional<Employee> findByEmail(String email);


    /**
     * Retrieves a summary of employees within a specified date range, ordered by their DU points.
     * <p>
     * This query calculates the DU points for each employee based on their participation duration in approved activities,
     * weighted by the activity's weightage per hour. The results are grouped by employee and ordered in descending order
     * of DU points.
     *
     * @param initialDate The start date of the date range for filtering participation records (inclusive).
     * @param endDate     The end date of the date range for filtering participation records (inclusive).
     * @return A list of {@link EmployeeSummaryDto} objects containing the summary of employees, including their DU points,
     * within the specified date range.
     */
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



    /**
     * Retrieves a summary of employees who participated in a specific activity within a date range, ordered by their points.
     *
     * @param activityId The ID of the activity.
     * @param initialDate The start date of the date range for filtering participation records (inclusive).
     * @param endDate The end date of the date range for filtering participation records (inclusive).
     * @return A list of {@link EmployeeActivitySummaryDto} objects containing the summary of employees with their points.
     */
    @Query("SELECT new com.perkpal.dto.EmployeeActivitySummaryDto(e.id, e.firstName, e.lastName, e.designation, e.email, " +
            "SUM((p.duration / 60.0) * a.weightagePerHour), e.photoUrl, e.duId.departmentName) " +
            "FROM Employee e " +
            "JOIN e.participation p " +
            "JOIN p.activityId a " +
            "WHERE p.approvalStatus = 'approved' " +
            "AND p.activityId.id = :activityId " +
            "AND p.participationDate BETWEEN :initialDate AND :endDate " +
            "GROUP BY e.id, e.firstName, e.lastName, e.designation, e.email, e.photoUrl, e.duId.departmentName " +
            "ORDER BY SUM((p.duration / 60.0) * a.weightagePerHour) DESC")
    List<EmployeeActivitySummaryDto> findEmployeesByActivityAndDateRange(@Param("activityId") Long activityId,
                                                                         @Param("initialDate") Timestamp initialDate,
                                                                         @Param("endDate") Timestamp endDate);

}