package com.ayurveda.diet.controller;

import com.ayurveda.diet.dto.AppointmentRequest;
import com.ayurveda.diet.dto.HealthParameterRequest;
import com.ayurveda.diet.model.*;
        import com.ayurveda.diet.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard(@PathVariable Long id) {
        try {
            Map<String, Object> response = patientService.getDashboard(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/health-parameter")
    public ResponseEntity<?> addHealthParameter(@RequestBody HealthParameterRequest request) {
        try {
            HealthParameter param = patientService.addHealthParameter(request);
            return ResponseEntity.ok(param);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/dietitians")
    public ResponseEntity<List<Dietitian>> getAllDietitians() {
        try {
            List<Dietitian> dietitians = patientService.getAllDietitians();
            return ResponseEntity.ok(dietitians);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/appointment")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequest request) {
        try {
            Appointment appointment = patientService.bookAppointment(request);
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/diet-charts")
    public ResponseEntity<List<DietChart>> getDietCharts(@PathVariable Long id) {
        try {
            List<DietChart> dietCharts = patientService.getDietCharts(id);
            return ResponseEntity.ok(dietCharts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}