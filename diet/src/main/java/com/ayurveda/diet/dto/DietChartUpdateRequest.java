package com.ayurveda.diet.dto;

import lombok.Data;
import java.util.List;

@Data
public class DietChartUpdateRequest {
    private Long chartId;
    private Integer durationDays;
    private List<MealUpdateRequest> meals;

    @Data
    public static class MealUpdateRequest {
        private Integer dayNumber;
        private String mealType;
        private String timing;
        private List<MealFoodRequest> mealFoods;
    }

    @Data
    public static class MealFoodRequest {
        private Long foodId;
        private Double quantity; // in grams
    }
}


