package com.ayurveda.diet.repository;


import com.ayurveda.diet.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDietitianIdAndStatusOrderByAppointmentDateAsc(Long dietitianId, String status);
    List<Appointment> findByPatientIdOrderByAppointmentDateDesc(Long patientId);
    long countByDietitianIdAndStatus(Long dietitianId, String status);
    List<Appointment> findByDietitianIdAndAppointmentDateAfterOrderByAppointmentDateAsc(Long dietitianId, LocalDateTime date);
}