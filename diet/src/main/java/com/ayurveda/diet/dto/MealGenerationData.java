package com.ayurveda.diet.dto;



import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class MealGenerationData {
    private String mealType;
    private String timing;
    private String foodItems;
    private int calories;
    private double protein;
    private double carbs;
    private double fats;
    private double fiber;
    private String rasa;
    private String virya;
    private String vipaka;
    private String dosha;
    private String benefits;
}