package com.ayurveda.diet.dto;


import lombok.Data;

@Data
public class FoodSearchRequest {
    private String query;
    private Double quantity = 100.0; // default 100g
}