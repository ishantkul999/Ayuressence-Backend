package com.ayurveda.diet.controller;

import com.ayurveda.diet.model.Food;
import com.ayurveda.diet.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/food")
@CrossOrigin(origins = "*")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam(required = false) String query) {
        try {
            List<Food> foods = foodService.searchFood(query);
            return ResponseEntity.ok(foods);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/nutrition")
    public ResponseEntity<Map<String, Object>> getFoodNutrition(
            @PathVariable Long id,
            @RequestParam(defaultValue = "100") Double quantity) {
        try {
            Map<String, Object> response = foodService.getFoodNutrition(id, quantity);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Food>> getAllFoods() {
        try {
            List<Food> foods = foodService.getAllFoods();
            return ResponseEntity.ok(foods);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}