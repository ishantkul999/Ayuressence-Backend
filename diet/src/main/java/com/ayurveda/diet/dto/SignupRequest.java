package com.ayurveda.diet.dto;


import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String role;
    private String name;

    // Dietitian specific
    private String phone;
    private String qualification;
    private Integer experience;
    private String specialization;

    // Patient specific
    private Integer age;
    private String gender;
    private Double weight;
    private Double height;
    private String healthCondition;
    private String allergies;
}
