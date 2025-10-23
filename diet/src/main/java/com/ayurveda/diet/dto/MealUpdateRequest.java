package com.ayurveda.diet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealUpdateRequest {
    private String timing;
    private List<MealFoodRequest> mealFoods;
    private String modificationNotes;
}