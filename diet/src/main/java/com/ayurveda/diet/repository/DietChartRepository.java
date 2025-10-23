/*package com.ayurveda.diet.repository;


import com.ayurveda.diet.model.DietChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Optional;
@Repository
public interface DietChartRepository extends JpaRepository<DietChart, Long> {
    List<DietChart> findByPatientIdOrderByCreatedDateDesc(Long patientId);
    List<DietChart> findByDietitianIdOrderByCreatedDateDesc(Long dietitianId);
    List<DietChart> findByPatientIdAndStatus(Long patientId, String status);
    long countByDietitianIdAndStatus(Long dietitianId, String status);
    Optional<DietChart> findTopByPatientIdOrderByCreatedDateDesc(Long patientId);
}*/


package com.ayurveda.diet.repository;

import com.ayurveda.diet.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

// DietChart Repository
/*@Repository
public interface DietChartRepository extends JpaRepository<DietChart, Long> {
    List<DietChart> findByPatientIdOrderByCreatedDateDesc(Long patientId);
    List<DietChart> findByDietitianIdOrderByCreatedDateDesc(Long dietitianId);
    List<DietChart> findByPatientIdAndStatus(Long patientId, String status);
}*/
/*@Repository
public interface DietChartRepository extends JpaRepository<DietChart, Long> {
    List<DietChart> findByPatientIdOrderByCreatedDateDesc(Long patientId);
    List<DietChart> findByDietitianIdOrderByCreatedDateDesc(Long dietitianId);
    List<DietChart> findByPatientIdAndStatus(Long patientId, String status);

    @Query("SELECT d FROM DietChart d WHERE d.patient.id = :patientId AND d.status = 'ACTIVE'")
    List<DietChart> findActiveDietChartsByPatientId(@Param("patientId") Long patientId);
}*/


import com.ayurveda.diet.model.DietChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DietChartRepository extends JpaRepository<DietChart, Long> {

    // Find by patient
    List<DietChart> findByPatientIdOrderByCreatedDateDesc(Long patientId);

    // Find by dietitian
    List<DietChart> findByDietitianIdOrderByCreatedDateDesc(Long dietitianId);

    // Find by patient and status
    List<DietChart> findByPatientIdAndStatus(Long patientId, String status);

    // Find by dietitian and status
    List<DietChart> findByDietitianIdAndStatus(Long dietitianId, String status);

    // Count by dietitian and status
    Long countByDietitianIdAndStatus(Long dietitianId, String status);

    // Count by patient
    Long countByPatientId(Long patientId);

    // Find active diet charts by patient
    @Query("SELECT d FROM DietChart d WHERE d.patient.id = :patientId AND d.status = 'ACTIVE'")
    List<DietChart> findActiveDietChartsByPatientId(@Param("patientId") Long patientId);

    // Find by date range
    @Query("SELECT d FROM DietChart d WHERE d.startDate >= :startDate AND d.endDate <= :endDate")
    List<DietChart> findByDateRange(@Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);

    // Find expiring diet charts (ending within specified days)
    @Query("SELECT d FROM DietChart d WHERE d.endDate BETWEEN :today AND :futureDate AND d.status = 'ACTIVE'")
    List<DietChart> findExpiringDietCharts(@Param("today") LocalDate today,
                                           @Param("futureDate") LocalDate futureDate);

    // Find diet charts by duration
    List<DietChart> findByDurationDays(Integer durationDays);

    // Get latest version for a patient
    @Query("SELECT d FROM DietChart d WHERE d.patient.id = :patientId " +
            "ORDER BY d.versionNumber DESC, d.createdDate DESC")
    List<DietChart> findLatestVersionByPatientId(@Param("patientId") Long patientId);

    // Find diet charts modified by specific dietitian
    List<DietChart> findByLastModifiedBy(String dietitianName);

    // Statistics queries
    @Query("SELECT AVG(d.totalCalories) FROM DietChart d WHERE d.dietitian.id = :dietitianId")
    Double getAverageCaloriesByDietitian(@Param("dietitianId") Long dietitianId);

    @Query("SELECT COUNT(d) FROM DietChart d WHERE d.dietitian.id = :dietitianId " +
            "AND d.createdDate >= :startDate AND d.createdDate <= :endDate")
    Long countDietChartsCreatedInPeriod(@Param("dietitianId") Long dietitianId,
                                        @Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);
}