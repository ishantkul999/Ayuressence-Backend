package com.ayurveda.diet.service;

import com.ayurveda.diet.model.Food;
import com.ayurveda.diet.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public List<Food> searchFood(String query) {
        if (query == null || query.trim().isEmpty()) {
            return foodRepository.findAll();
        }
        return foodRepository.findByNameContainingIgnoreCase(query);
    }

    public Map<String, Object> getFoodNutrition(Long foodId, Double quantity) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        double factor = quantity / 100.0;

        Map<String, Object> response = new HashMap<>();
        response.put("name", food.getName());
        response.put("category", food.getCategory());
        response.put("quantity", quantity);

        // Adjusted nutrition
        Map<String, Double> nutrition = new HashMap<>();
        nutrition.put("calories", Math.round(food.getCalories() * factor * 100.0) / 100.0);
        nutrition.put("protein", Math.round(food.getProtein() * factor * 100.0) / 100.0);
        nutrition.put("carbs", Math.round(food.getCarbs() * factor * 100.0) / 100.0);
        nutrition.put("fats", Math.round(food.getFats() * factor * 100.0) / 100.0);
        nutrition.put("fiber", Math.round(food.getFiber() * factor * 100.0) / 100.0);
        nutrition.put("calcium", Math.round(food.getCalcium() * factor * 100.0) / 100.0);
        nutrition.put("iron", Math.round(food.getIron() * factor * 100.0) / 100.0);
        nutrition.put("vitaminC", Math.round(food.getVitaminC() * factor * 100.0) / 100.0);
        nutrition.put("vitaminA", Math.round(food.getVitaminA() * factor * 100.0) / 100.0);
        response.put("nutrition", nutrition);

        // Ayurvedic properties
        Map<String, String> ayurvedic = new HashMap<>();
        ayurvedic.put("rasa", food.getRasa());
        ayurvedic.put("virya", food.getVirya());
        ayurvedic.put("vipaka", food.getVipaka());
        ayurvedic.put("dosha", food.getDosha());
        ayurvedic.put("digestibility", food.getDigestibility());
        ayurvedic.put("benefits", food.getBenefits());
        response.put("ayurvedic", ayurvedic);

        return response;
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }
}