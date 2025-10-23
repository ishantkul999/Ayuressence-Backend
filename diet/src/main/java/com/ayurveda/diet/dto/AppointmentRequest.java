package com.ayurveda.diet.dto;



import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private Long patientId;
    private Long dietitianId;
    private LocalDateTime appointmentDate;
    private String notes;
}