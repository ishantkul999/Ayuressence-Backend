package com.ayurveda.diet.repository;

import com.ayurveda.diet.model.HealthParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HealthParameterRepository extends JpaRepository<HealthParameter, Long> {
    List<HealthParameter> findByPatientIdOrderByDateDesc(Long patientId);
    Optional<HealthParameter> findByPatientIdAndDate(Long patientId, LocalDate date);
}