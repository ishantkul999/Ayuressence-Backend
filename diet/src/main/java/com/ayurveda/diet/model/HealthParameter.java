package com.ayurveda.diet.model;
import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "health_parameters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDate date;
    private Double weight;
    private Integer mealFrequency;
    private String bowelMovement; // Regular, Irregular, Constipated
    private Double waterIntake; // liters
    private Integer caloriesBurnt;
    private Integer sleepHours;
    private String stressLevel; // Low, Medium, High
    private String energyLevel; // Low, Medium, High
}
