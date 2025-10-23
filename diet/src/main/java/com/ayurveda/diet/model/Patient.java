package com.ayurveda.diet.model;

import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String email;
    private Integer age;
    private String gender;
    private Double weight; // kg
    private Double height; // cm
    private String healthCondition;
    private String allergies;
    private Double bmi;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    private Dietitian assignedDietitian;
}
