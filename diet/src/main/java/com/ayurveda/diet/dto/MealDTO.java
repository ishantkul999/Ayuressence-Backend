package com.ayurveda.diet.dto;



import lombok.Data;
import java.util.List;

/*@Data
public class MealDTO {
    private Long id;
    private Integer dayNumber;
    private String mealType;
    private String timing;
    private String foodItems;
    private List<MealFoodDTO> mealFoods;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fats;
    private Double fiber;
    private String rasa;
    private String virya;
    private String vipaka;
    private String dosha;
    private String benefits;
}*/


import lombok.Data;

import java.util.List;

@Data
public class MealDTO {
    private Integer dayNumber;
    private String mealType;
    private String timing;
    private String foodItems;

    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fats;
    private Double fiber;

    private String rasa;
    private String virya;
    private String vipaka;
    private String dosha;
    private String benefits;

    private List<MealFoodDTO> mealFoods;
}

