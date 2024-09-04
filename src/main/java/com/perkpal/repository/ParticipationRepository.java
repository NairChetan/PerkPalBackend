package com.perkpal.repository;

import com.perkpal.dto.*;

import com.perkpal.entity.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    /**
     * Finds all participations with the specified approval status, returned in a paginated format.
     *
     * @param approvalStatus The approval status to filter participations by (e.g., "approved", "pending").
     * @param pageable       The pagination information (page number, page size, sorting).
     * @return A paginated list of participations with the specified approval status.
     */
    Page<Participation> findByApprovalStatus(String approvalStatus, Pageable pageable);


    /**
     * Calculates the total participation points for each employee within a specified date range.
     *
     * @param initialDate The start date of the range for filtering participation records (inclusive).
     * @param endDate     The end date of the range for filtering participation records (inclusive).
     * @return A list of objects, where each object is an array containing the employee ID and the sum of their participation points.
     */
    @Query("SELECT p.employee.id, SUM(p.duration) " +
            "FROM Participation p " +
            "WHERE p.approvalStatus = 'approved' " +
            "AND p.participationDate BETWEEN :initialDate AND :endDate " +
            "GROUP BY p.employee.id")
    List<Object[]> findEmployeePointsInDateRange(@Param("initialDate") Timestamp initialDate,
                                                 @Param("endDate") Timestamp endDate);


    /**
     * Finds all participation records for a specific employee on a given date.
     *
     * @param date       The date for which to find participation records.
     * @param employeeId The ID of the employee whose participation records are being queried.
     * @return A list of participation records for the specified employee on the given date.
     */
    @Query(value = "SELECT * FROM participation WHERE DATE(participation_date) = :date AND emp_id = :employeeId", nativeQuery = true)
    List<Participation> findByParticipationDateAndEmployeeId(
            @Param("date") LocalDate date,
            @Param("employeeId") Long employeeId);


    /**
     * Finds all participation records for a specific employee.
     *
     * @param employeeId The ID of the employee whose participation records are being queried.
     * @return A list of all participation records associated with the specified employee.
     */
    @Query(value = "SELECT * FROM participation WHERE emp_id = :employeeId", nativeQuery = true)
    List<Participation> findByParticipationEmployeeId(
            @Param("employeeId") Long employeeId);


    /**
     * Retrieves the leaderboard of employees based on their DU points for a specified year.
     * <p>
     * This query calculates the DU points for each employee by summing the weighted duration of their approved participations
     * within the specified year. The results are grouped by employee and ordered in descending order of total points.
     *
     * @param year The year for which to calculate the leaderboard.
     * @return A list of {@link EmployeeLeaderBoardDto} objects representing the employee leaderboard for the specified year,
     * ordered by DU points in descending order.
     */
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
            "ORDER BY SUM(a.weightagePerHour * (p.duration / 60.0)) DESC")
    // Ordering by totalPoints
    List<EmployeeLeaderBoardDto> findEmployeeLeaderboardByYear(@Param("year") int year);

    @Query("SELECT new com.perkpal.dto.PointsAccumulatedOverYearsDto(YEAR(p.participationDate) AS year, SUM(a.weightagePerHour * p.duration / 60) AS pointsAccumulated) " +
            "FROM Participation p " +
            "JOIN p.activityId a " +
            "WHERE p.employee.id = :empId " +
            "AND p.approvalStatus = 'approved' " +
            "AND YEAR(p.participationDate) >= YEAR(CURRENT_DATE) - 4 " +
            "GROUP BY YEAR(p.participationDate) " +
            "ORDER BY year DESC")
    List<PointsAccumulatedOverYearsDto> findApprovedPointsForLastFourYears(@Param("empId") Long empId);

    @Query("SELECT new com.perkpal.dto.PointsAccumulatedPerMonthDto(MONTH(p.participationDate), SUM(a.weightagePerHour * p.duration / 60)) " +
            "FROM Participation p " +
            "JOIN p.activityId a " +
            "WHERE p.employee.id = :employeeId " +
            "AND p.approvalStatus = 'approved' " +
            "AND YEAR(p.participationDate) = YEAR(CURRENT_DATE) " +
            "GROUP BY MONTH(p.participationDate) " +
            "ORDER BY MONTH(p.participationDate)")
    List<PointsAccumulatedPerMonthDto> findApprovedPointsPerMonthForCurrentYear(Long employeeId);

    @Query("SELECT new com.perkpal.dto.ParticipationDetailsFetchForPendingApprovalDto(p.id, e.firstName, e.lastName, e.id, a.activityName, a.categoryId.categoryName, p.participationDate, p.duration, p.description, p.proofUrl) " +
            "FROM Participation p " +
            "JOIN p.employee e " +
            "JOIN p.activityId a " +
            "WHERE (:activityName IS NULL OR a.activityName LIKE %:activityName%) " +
            "AND (:firstName IS NULL OR e.firstName LIKE %:firstName%) " +
            "AND (:lastName IS NULL OR e.lastName LIKE %:lastName%) " +
            "AND (:employeeId IS NULL OR e.id = :employeeId)" +
            "AND p.approvalStatus = 'pending'")
    Page<ParticipationDetailsFetchForPendingApprovalDto> searchParticipation(
            String activityName, String firstName, String lastName, Integer employeeId, Pageable pageable);
}