package com.ayurveda.diet.model;


import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    private Dietitian dietitian;

    private LocalDateTime appointmentDate;
    private String status = "PENDING"; // PENDING, COMPLETED, CANCELLED
    private String notes;
    private LocalDateTime createdAt = LocalDateTime.now();
}