package com.ayurveda.diet.dto;


import lombok.Data;

/*@Data
public class MealFoodDTO {
    private Long id;
    private Long foodId;  // links to Food database entry
    private Double quantity;
    private String unit;
}*/


import lombok.Data;

@Data
public class MealFoodDTO {
    private Long foodId;
    private Double quantity; // in grams
}

