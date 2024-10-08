package com.perkpal.repository;

import com.perkpal.dto.EmployeeActivitySummaryDto;
import com.perkpal.dto.EmployeeParticipationDetailsDto;
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
     * @param initialDate The start date of the date range for filtering participation records (inclusive).
     * @param endDate The end date of the date range for filtering participation records (inclusive).
     * @return A list of {@link EmployeeActivitySummaryDto} objects containing the summary of employees with their points.
     */
    @Query("SELECT e.id AS employeeId, e.firstName, e.lastName, e.duId.departmentName AS duDepartmentName, e.clubId.clubName AS clubName, \n" +
            " p.id AS participationId, a.activityName, p.duration, p.remarks, p.participationDate, p.approvalDate, \n" +
            "       p.description, p.proofUrl\n" +
            "FROM Employee e\n" +
            "JOIN e.participation p\n" +
            "JOIN p.activityId a\n" +
            "WHERE p.approvalStatus = 'approved'\n" +
            "AND p.participationDate BETWEEN :initialDate AND :endDate\n" +
            "AND a.activityName = :activityName\n" +
            "GROUP BY e.id, e.firstName, e.lastName, e.duId.departmentName, e.clubId.clubName, e.photoUrl, p.id, a.activityName, \n" +
            "         p.duration, p.remarks, p.participationDate, p.approvalDate, p.description, p.proofUrl\n" +
            "ORDER BY employeeId ")
    List<Object[]> findEmployeeParticipationDetailsByActivityAndDateRange(
            @Param("initialDate") Timestamp initialDate,
            @Param("endDate") Timestamp endDate,
            @Param("activityName") String activityName);
}