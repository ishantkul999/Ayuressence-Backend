/*package com.ayurveda.diet.controller;

import com.ayurveda.diet.dto.DashboardResponse;
import com.ayurveda.diet.dto.DietChartRequest;
import com.ayurveda.diet.model.*;
        import com.ayurveda.diet.service.DietChartService;
import com.ayurveda.diet.service.DietitianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dietitian")
@CrossOrigin(origins = "*")
public class DietitianController {

    @Autowired
    private DietitianService dietitianService;

    @Autowired
    private DietChartService dietChartService;

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(@PathVariable Long id) {
        try {
            DashboardResponse response = dietitianService.getDashboard(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients(@RequestParam Long dietitianId) {
        try {
            List<Patient> patients = dietitianService.getAllPatients(dietitianId);
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getPatientProfile(@PathVariable Long patientId) {
        try {
            Map<String, Object> response = dietitianService.getPatientProfile(patientId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/diet-chart/generate")
    public ResponseEntity<?> generateDietChart(@RequestBody DietChartRequest request) {
        try {
            DietChart dietChart = dietChartService.generateDietChart(request);
            return ResponseEntity.ok(dietChart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<Appointment>> getUpcomingAppointments(@PathVariable Long id) {
        try {
            List<Appointment> appointments = dietitianService.getUpcomingAppointments(id);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/appointment/{id}/status")
    public ResponseEntity<?> updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        try {
            Appointment appointment = dietitianService.updateAppointmentStatus(id, status);
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}*/
package com.ayurveda.diet.controller;

import com.ayurveda.diet.dto.*;
import com.ayurveda.diet.model.*;
import com.ayurveda.diet.service.DietChartService;
import com.ayurveda.diet.service.DietitianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dietitian")
@CrossOrigin(origins = "*")
public class DietitianController {

    @Autowired
    private DietitianService dietitianService;

    @Autowired
    private DietChartService dietChartService;

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(@PathVariable Long id) {
        try {
            DashboardResponse response = dietitianService.getDashboard(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients(@RequestParam Long dietitianId) {
        try {
            List<Patient> patients = dietitianService.getAllPatients(dietitianId);
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getPatientProfile(@PathVariable Long patientId) {
        try {
            Map<String, Object> response = dietitianService.getPatientProfile(patientId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/diet-chart/generate")
    public ResponseEntity<?> generateDietChart(@RequestBody DietChartRequest request) {
        try {
            DietChart dietChart = dietChartService.generateDietChart(request);
            return ResponseEntity.ok(dietChart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // NEW: Get full detailed diet chart for dietitian to view and edit
    @GetMapping("/diet-chart/{dietChartId}/details")
    public ResponseEntity<?> getDietChartDetails(@PathVariable Long dietChartId) {
        try {
            DietChart dietChart = dietChartService.getDietChartWithFullDetails(dietChartId);
            return ResponseEntity.ok(dietChart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // NEW: Get patient's active diet charts
    @GetMapping("/patient/{patientId}/diet-charts")
    public ResponseEntity<?> getPatientDietCharts(@PathVariable Long patientId) {
        try {
            List<DietChart> dietCharts = dietChartService.getPatientDietCharts(patientId);
            return ResponseEntity.ok(dietCharts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // NEW: Update a meal by adding/removing/modifying food items
    @PutMapping("/diet-chart/{dietChartId}/meal/{mealId}")
    public ResponseEntity<?> updateMeal(
            @PathVariable Long dietChartId,
            @PathVariable Long mealId,
            @RequestBody MealUpdateRequest request,
            @RequestParam Long dietitianId) {
        try {
            DietChart updatedChart = dietChartService.updateMeal(
                    dietChartId, mealId, request, dietitianId);
            return ResponseEntity.ok(updatedChart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // NEW: Add a new food item to an existing meal
    @PostMapping("/diet-chart/{dietChartId}/meal/{mealId}/food")
    public ResponseEntity<?> addFoodToMeal(
            @PathVariable Long dietChartId,
            @PathVariable Long mealId,
            @RequestBody MealFoodRequest request,
            @RequestParam Long dietitianId) {
        try {
            // Add logging to debug
            System.out.println("Adding food to meal:");
            System.out.println("Diet Chart ID: " + dietChartId);
            System.out.println("Meal ID: " + mealId);
            System.out.println("Food ID: " + request.getFoodId());
            System.out.println("Quantity: " + request.getQuantity());
            System.out.println("Dietitian ID: " + dietitianId);

            DietChart updatedChart = dietChartService.addFoodToMeal(
                    dietChartId, mealId, request, dietitianId);
            return ResponseEntity.ok(updatedChart);
        } catch (Exception e) {
            e.printStackTrace(); // Print full stack trace
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // NEW: Remove a food item from a meal
    @DeleteMapping("/diet-chart/{dietChartId}/meal/{mealId}/food/{mealFoodId}")
    public ResponseEntity<?> removeFoodFromMeal(
            @PathVariable Long dietChartId,
            @PathVariable Long mealId,
            @PathVariable Long mealFoodId,
            @RequestParam Long dietitianId) {
        try {
            DietChart updatedChart = dietChartService.removeFoodFromMeal(
                    dietChartId, mealId, mealFoodId, dietitianId);
            return ResponseEntity.ok(updatedChart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // NEW: Update quantity of existing food in meal
    @PatchMapping("/diet-chart/{dietChartId}/meal/{mealId}/food/{mealFoodId}")
    public ResponseEntity<?> updateFoodQuantity(
            @PathVariable Long dietChartId,
            @PathVariable Long mealId,
            @PathVariable Long mealFoodId,
            @RequestBody Map<String, Double> request,
            @RequestParam Long dietitianId) {
        try {
            Double newQuantity = request.get("quantity");
            DietChart updatedChart = dietChartService.updateFoodQuantity(
                    dietChartId, mealId, mealFoodId, newQuantity, dietitianId);
            return ResponseEntity.ok(updatedChart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // NEW: Get all available foods from database for selection
    @GetMapping("/foods")
    public ResponseEntity<?> getAllFoods(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String dosha) {
        try {
            List<Food> foods = dietChartService.getAvailableFoods(category, dosha);
            return ResponseEntity.ok(foods);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // NEW: Search foods by name
    @GetMapping("/foods/search")
    public ResponseEntity<?> searchFoods(@RequestParam String query) {
        try {
            List<Food> foods = dietChartService.searchFoods(query);
            return ResponseEntity.ok(foods);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<Appointment>> getUpcomingAppointments(@PathVariable Long id) {
        try {
            List<Appointment> appointments = dietitianService.getUpcomingAppointments(id);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/appointment/{id}/status")
    public ResponseEntity<?> updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        try {
            Appointment appointment = dietitianService.updateAppointmentStatus(id, status);
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}