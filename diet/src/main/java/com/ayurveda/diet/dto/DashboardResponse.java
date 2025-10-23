package com.ayurveda.diet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Long totalPatients;
    private Long activeDietCharts;
    private Long upcomingAppointments;
    private Long totalConsultations;
}