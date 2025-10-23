/*package com.ayurveda.diet.service;

import com.ayurveda.diet.dto.DashboardResponse;
import com.ayurveda.diet.model.*;
        import com.ayurveda.diet.repository.*;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DietitianService {

    @Autowired
    private DietitianRepository dietitianRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DietChartRepository dietChartRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private HealthParameterRepository healthParameterRepository;

    public DashboardResponse getDashboard(Long dietitianId) {
        long totalPatients = patientRepository.findByAssignedDietitianId(dietitianId).size();
        long activeDietCharts = dietChartRepository.countByDietitianIdAndStatus(dietitianId, "ACTIVE");
        long upcomingAppointments = appointmentRepository.countByDietitianIdAndStatus(dietitianId, "PENDING");

        Dietitian dietitian = dietitianRepository.findById(dietitianId)
                .orElseThrow(() -> new RuntimeException("Dietitian not found"));
        long totalConsultations = dietitian.getTotalConsultations();

        return new DashboardResponse(totalPatients, activeDietCharts, upcomingAppointments, totalConsultations);
    }

    public List<Patient> getAllPatients(Long dietitianId) {
        return patientRepository.findByAssignedDietitianId(dietitianId);
    }

    public Map<String, Object> getPatientProfile(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<HealthParameter> healthParams = healthParameterRepository
                .findByPatientIdOrderByDateDesc(patientId);

        List<DietChart> dietCharts = dietChartRepository
                .findByPatientIdOrderByCreatedDateDesc(patientId);

        Map<String, Object> response = new HashMap<>();
        response.put("patient", patient);
        response.put("healthParameters", healthParams);
        response.put("dietCharts", dietCharts);

        return response;
    }

    public List<Appointment> getUpcomingAppointments(Long dietitianId) {
        return appointmentRepository.findByDietitianIdAndAppointmentDateAfterOrderByAppointmentDateAsc(
                dietitianId, LocalDateTime.now());
    }

    public Appointment updateAppointmentStatus(Long appointmentId, String status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(status);

        if ("COMPLETED".equals(status)) {
            Dietitian dietitian = appointment.getDietitian();
            dietitian.setTotalConsultations(dietitian.getTotalConsultations() + 1);
            dietitianRepository.save(dietitian);
        }

        return appointmentRepository.save(appointment);
    }
}*/
package com.ayurveda.diet.service;

import com.ayurveda.diet.dto.DashboardResponse;
import com.ayurveda.diet.model.*;
import com.ayurveda.diet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DietitianService {

    @Autowired
    private DietitianRepository dietitianRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DietChartRepository dietChartRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private HealthParameterRepository healthParameterRepository;

    @Autowired
    private MealRepository mealRepository;

    public DashboardResponse getDashboard(Long dietitianId) {
        long totalPatients = patientRepository.findByAssignedDietitianId(dietitianId).size();
        long activeDietCharts = dietChartRepository.countByDietitianIdAndStatus(dietitianId, "ACTIVE");
        long upcomingAppointments = appointmentRepository.countByDietitianIdAndStatus(dietitianId, "PENDING");

        Dietitian dietitian = dietitianRepository.findById(dietitianId)
                .orElseThrow(() -> new RuntimeException("Dietitian not found"));
        long totalConsultations = dietitian.getTotalConsultations();

        return new DashboardResponse(totalPatients, activeDietCharts, upcomingAppointments, totalConsultations);
    }

    public List<Patient> getAllPatients(Long dietitianId) {
        return patientRepository.findByAssignedDietitianId(dietitianId);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getPatientProfile(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<HealthParameter> healthParams = healthParameterRepository
                .findByPatientIdOrderByDateDesc(patientId);

        List<DietChart> dietCharts = dietChartRepository
                .findByPatientIdOrderByCreatedDateDesc(patientId);

        Map<String, Object> response = new HashMap<>();
        response.put("patient", patient);
        response.put("healthParameters", healthParams);
        response.put("dietCharts", dietCharts);

        return response;
    }

    // NEW: Get detailed diet chart information for editing
    @Transactional(readOnly = true)
    public Map<String, Object> getDietChartDetails(Long dietChartId) {
        DietChart dietChart = dietChartRepository.findById(dietChartId)
                .orElseThrow(() -> new RuntimeException("Diet Chart not found"));

        // Get meals ordered by day and meal type
        List<Meal> orderedMeals = mealRepository.findByDietChartIdOrderedByDayAndMealType(dietChartId);

        Map<String, Object> response = new HashMap<>();
        response.put("dietChart", dietChart);
        response.put("meals", orderedMeals);
        response.put("patient", dietChart.getPatient());
        response.put("dietitian", dietChart.getDietitian());
        response.put("totalMeals", orderedMeals.size());

        // Add summary statistics
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalCalories", dietChart.getTotalCalories());
        summary.put("totalProtein", dietChart.getTotalProtein());
        summary.put("totalCarbs", dietChart.getTotalCarbs());
        summary.put("totalFats", dietChart.getTotalFats());
        summary.put("totalFiber", dietChart.getTotalFiber());
        summary.put("durationDays", dietChart.getDurationDays());
        summary.put("status", dietChart.getStatus());
        summary.put("versionNumber", dietChart.getVersionNumber());
        summary.put("lastModifiedBy", dietChart.getLastModifiedBy());
        summary.put("lastModifiedDate", dietChart.getLastModifiedDate());

        response.put("summary", summary);

        return response;
    }

    // NEW: Get all active diet charts for a dietitian
    @Transactional(readOnly = true)
    public List<DietChart> getActiveDietCharts(Long dietitianId) {
        return dietChartRepository.findByDietitianIdAndStatus(dietitianId, "ACTIVE");
    }

    // NEW: Get diet chart summary for patient list view
    @Transactional(readOnly = true)
    public Map<String, Object> getDietChartSummary(Long patientId) {
        List<DietChart> activeDietCharts = dietChartRepository
                .findByPatientIdAndStatus(patientId, "ACTIVE");

        List<DietChart> allDietCharts = dietChartRepository
                .findByPatientIdOrderByCreatedDateDesc(patientId);

        Map<String, Object> summary = new HashMap<>();
        summary.put("activeCharts", activeDietCharts);
        summary.put("totalCharts", allDietCharts.size());
        summary.put("hasActiveChart", !activeDietCharts.isEmpty());

        if (!activeDietCharts.isEmpty()) {
            DietChart activeChart = activeDietCharts.get(0);
            summary.put("currentChartId", activeChart.getId());
            summary.put("currentChartDuration", activeChart.getDurationDays());
            summary.put("currentChartStartDate", activeChart.getStartDate());
            summary.put("currentChartEndDate", activeChart.getEndDate());
        }

        return summary;
    }

    public List<Appointment> getUpcomingAppointments(Long dietitianId) {
        return appointmentRepository.findByDietitianIdAndAppointmentDateAfterOrderByAppointmentDateAsc(
                dietitianId, LocalDateTime.now());
    }

    @Transactional
    public Appointment updateAppointmentStatus(Long appointmentId, String status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(status);

        if ("COMPLETED".equals(status)) {
            Dietitian dietitian = appointment.getDietitian();
            dietitian.setTotalConsultations(dietitian.getTotalConsultations() + 1);
            dietitianRepository.save(dietitian);
        }

        return appointmentRepository.save(appointment);
    }

    // NEW: Validate dietitian access to diet chart
    @Transactional(readOnly = true)
    public boolean validateDietitianAccess(Long dietitianId, Long dietChartId) {
        DietChart dietChart = dietChartRepository.findById(dietChartId)
                .orElseThrow(() -> new RuntimeException("Diet Chart not found"));

        return dietChart.getDietitian().getId().equals(dietitianId);
    }

    // NEW: Get patient's current active diet chart
    @Transactional(readOnly = true)
    public DietChart getPatientActiveDietChart(Long patientId) {
        List<DietChart> activeCharts = dietChartRepository
                .findByPatientIdAndStatus(patientId, "ACTIVE");

        if (activeCharts.isEmpty()) {
            throw new RuntimeException("No active diet chart found for patient");
        }

        return activeCharts.get(0); // Return the first active chart
    }
}