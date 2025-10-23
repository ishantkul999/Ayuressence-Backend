package com.ayurveda.diet.dto;



import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

@Data
public class GenerateDietChartRequest {
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Dietitian ID is required")
    private Long dietitianId;

    @Min(value = 1, message = "Duration must be at least 1 day")
    @Max(value = 30, message = "Duration cannot exceed 30 days")
    private int durationDays;
}