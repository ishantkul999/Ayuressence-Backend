package com.ayurveda.diet.model;


import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dietitians")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dietitian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String email;
    private String phone;
    private String qualification;
    private Integer experience; // years
    private String specialization;
    private Double rating = 4.5;
    private Integer totalConsultations = 0;
}