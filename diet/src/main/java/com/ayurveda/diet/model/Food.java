package com.ayurveda.diet.model;

/*import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category; // Grain, Vegetable, Fruit, Protein, Dairy, etc.

    // Nutritional Info (per 100g)
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fats;
    private Double fiber;
    private Double calcium;
    private Double iron;
    private Double vitaminC;
    private Double vitaminA;

    // Ayurvedic Properties
    private String rasa; // Sweet, Sour, Salty, Bitter, Pungent, Astringent
    private String virya; // Hot, Cold
    private String vipaka; // Sweet, Sour, Pungent
    private String dosha; // Vata+, Pitta-, Kapha+ etc.
    private String digestibility; // Easy, Moderate, Heavy
    private String benefits;
}*/


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category; // Grain, Vegetable, Fruit, Protein, Dairy, etc.

    // Nutritional Info (per 100g)
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fats;
    private Double fiber;
    private Double calcium;
    private Double iron;
    private Double vitaminC;
    private Double vitaminA;

    // Ayurvedic Properties
    private String rasa; // Sweet, Sour, Salty, Bitter, Pungent, Astringent
    private String virya; // Hot, Cold
    private String vipaka; // Sweet, Sour, Pungent
    private String dosha; // Vata+, Pitta-, Kapha+ etc.
    private String digestibility; // Easy, Moderate, Heavy
    private String benefits;
}
