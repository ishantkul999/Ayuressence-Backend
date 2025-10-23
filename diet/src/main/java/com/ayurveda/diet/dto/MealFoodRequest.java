package com.ayurveda.diet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealFoodRequest {
    private Long foodId; // ID from Food database
    private Double quantity; // in grams
}