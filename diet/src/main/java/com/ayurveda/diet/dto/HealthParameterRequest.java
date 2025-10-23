package com.ayurveda.diet.dto;



import lombok.Data;
import java.time.LocalDate;

@Data
public class HealthParameterRequest {
    private Long patientId;
    private LocalDate date;
    private Double weight;
    private Integer mealFrequency;
    private String bowelMovement;
    private Double waterIntake;
    private Integer caloriesBurnt;
    private Integer sleepHours;
    private String stressLevel;
    private String energyLevel;
}