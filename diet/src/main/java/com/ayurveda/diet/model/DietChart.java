package com.ayurveda.diet.model;


/*import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diet_charts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    private Dietitian dietitian;

    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer durationDays;
    private String status = "ACTIVE"; // ACTIVE, COMPLETED, CANCELLED

    @OneToMany(mappedBy = "dietChart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meal> meals = new ArrayList<>();

    // Nutritional Summary
    private Double totalCalories;
    private Double totalProtein;
    private Double totalCarbs;
    private Double totalFats;
    private Double totalFiber;
}*/

/*import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diet_charts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    private Dietitian dietitian;

    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer durationDays;
    private String status = "ACTIVE";

    @OneToMany(mappedBy = "dietChart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Meal> meals = new ArrayList<>();

    private Double totalCalories;
    private Double totalProtein;
    private Double totalCarbs;
    private Double totalFats;
    private Double totalFiber;
}*/


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diet_charts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    private Dietitian dietitian;

    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer durationDays;
    private String status = "ACTIVE";

    @OneToMany(mappedBy = "dietChart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Meal> meals = new ArrayList<>();

    // Nutritional totals
    private Double totalCalories;
    private Double totalProtein;
    private Double totalCarbs;
    private Double totalFats;
    private Double totalFiber;

    // NEW FIELDS FOR TRACKING MODIFICATIONS
    private LocalDateTime lastModifiedDate;

    private String lastModifiedBy; // Stores dietitian name who modified

    private Integer versionNumber = 1; // Track version of diet chart

    @Column(columnDefinition = "TEXT")
    private String modificationNotes; // Dietitian can add notes about changes

    // Helper method to increment version on modification
    public void incrementVersion() {
        this.versionNumber = (this.versionNumber == null ? 1 : this.versionNumber) + 1;
        this.lastModifiedDate = LocalDateTime.now();
    }

    // Helper method to recalculate totals from meals
    public void recalculateTotals() {
        this.totalCalories = meals.stream()
                .mapToDouble(meal -> meal.getCalories() != null ? meal.getCalories() : 0.0)
                .sum();
        this.totalProtein = meals.stream()
                .mapToDouble(meal -> meal.getProtein() != null ? meal.getProtein() : 0.0)
                .sum();
        this.totalCarbs = meals.stream()
                .mapToDouble(meal -> meal.getCarbs() != null ? meal.getCarbs() : 0.0)
                .sum();
        this.totalFats = meals.stream()
                .mapToDouble(meal -> meal.getFats() != null ? meal.getFats() : 0.0)
                .sum();
        this.totalFiber = meals.stream()
                .mapToDouble(meal -> meal.getFiber() != null ? meal.getFiber() : 0.0)
                .sum();

        // Round to 2 decimal places
        this.totalCalories = Math.round(this.totalCalories * 100.0) / 100.0;
        this.totalProtein = Math.round(this.totalProtein * 100.0) / 100.0;
        this.totalCarbs = Math.round(this.totalCarbs * 100.0) / 100.0;
        this.totalFats = Math.round(this.totalFats * 100.0) / 100.0;
        this.totalFiber = Math.round(this.totalFiber * 100.0) / 100.0;
    }
}
