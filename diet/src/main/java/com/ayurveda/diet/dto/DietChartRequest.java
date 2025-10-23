package com.ayurveda.diet.dto;

import lombok.Data;

@Data
public class DietChartRequest {
    private Long patientId;
    private Long dietitianId;
    private Integer durationDays;
}
