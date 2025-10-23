package com.ayurveda.diet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietChartModificationRequest {
    private String modificationNotes;
    private Long dietitianId;
}