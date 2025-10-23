package com.ayurveda.diet.service;


import com.ayurveda.diet.dto.AppointmentRequest;
import com.ayurveda.diet.dto.HealthParameterRequest;
import com.ayurveda.diet.model.*;
        import com.ayurveda.diet.repository.*;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DietitianRepository dietitianRepository;

    @Autowired
    private HealthParameterRepository healthParameterRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DietChartRepository dietChartRepository;

    public Map<String, Object> getDashboard(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<HealthParameter> recentParams = healthParameterRepository
                .findByPatientIdOrderByDateDesc(patientId);

        List<Appointment> recentAppointments = appointmentRepository
                .findByPatientIdOrderByAppointmentDateDesc(patientId);

        Map<String, Object> response = new HashMap<>();
        response.put("patient", patient);
        response.put("healthParameters", recentParams.isEmpty() ? null : recentParams.get(0));
        response.put("recentAppointments", recentAppointments);

        return response;
    }

    @Transactional
    public HealthParameter addHealthParameter(HealthParameterRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        HealthParameter param = new HealthParameter();
        param.setPatient(patient);
        param.setDate(request.getDate() != null ? request.getDate() : LocalDate.now());
        param.setWeight(request.getWeight());
        param.setMealFrequency(request.getMealFrequency());
        param.setBowelMovement(request.getBowelMovement());
        param.setWaterIntake(request.getWaterIntake());
        param.setCaloriesBurnt(request.getCaloriesBurnt());
        param.setSleepHours(request.getSleepHours());
        param.setStressLevel(request.getStressLevel());
        param.setEnergyLevel(request.getEnergyLevel());

        // Update patient's current weight
        if (request.getWeight() != null) {
            patient.setWeight(request.getWeight());
            if (patient.getHeight() != null) {
                double heightInMeters = patient.getHeight() / 100.0;
                double bmi = request.getWeight() / (heightInMeters * heightInMeters);
                patient.setBmi(Math.round(bmi * 100.0) / 100.0);
            }
            patientRepository.save(patient);
        }

        return healthParameterRepository.save(param);
    }

    public List<Dietitian> getAllDietitians() {
        return dietitianRepository.findAll();
    }

    @Transactional
    public Appointment bookAppointment(AppointmentRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Dietitian dietitian = dietitianRepository.findById(request.getDietitianId())
                .orElseThrow(() -> new RuntimeException("Dietitian not found"));

        // Assign dietitian to patient if not already assigned
        if (patient.getAssignedDietitian() == null) {
            patient.setAssignedDietitian(dietitian);
            patientRepository.save(patient);
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDietitian(dietitian);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setNotes(request.getNotes());
        appointment.setStatus("PENDING");

        return appointmentRepository.save(appointment);
    }

    public List<DietChart> getDietCharts(Long patientId) {
        return dietChartRepository.findByPatientIdOrderByCreatedDateDesc(patientId);
    }
}