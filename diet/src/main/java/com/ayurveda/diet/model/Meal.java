package com.ayurveda.diet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_chart_id", nullable = false)
    @JsonBackReference
    private DietChart dietChart;

    private Integer dayNumber;
    private String mealType; // BREAKFAST, LUNCH, DINNER, SNACK
    private String timing;

    @Deprecated // Use mealFoods list instead
    private String foodItems;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<MealFood> mealFoods = new ArrayList<>();

    // Nutritional totals (calculated from mealFoods)
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fats;
    private Double fiber;

    // Ayurvedic properties (aggregated from foods)
    private String rasa;
    private String virya;
    private String vipaka;
    private String dosha;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    // Helper method to recalculate meal totals from mealFoods
    public void recalculateTotals() {
        this.calories = mealFoods.stream()
                .mapToDouble(mf -> mf.getCalories() != null ? mf.getCalories() : 0.0)
                .sum();
        this.protein = mealFoods.stream()
                .mapToDouble(mf -> mf.getProtein() != null ? mf.getProtein() : 0.0)
                .sum();
        this.carbs = mealFoods.stream()
                .mapToDouble(mf -> mf.getCarbs() != null ? mf.getCarbs() : 0.0)
                .sum();
        this.fats = mealFoods.stream()
                .mapToDouble(mf -> mf.getFats() != null ? mf.getFats() : 0.0)
                .sum();
        this.fiber = mealFoods.stream()
                .mapToDouble(mf -> mf.getFiber() != null ? mf.getFiber() : 0.0)
                .sum();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DietChart getDietChart() {
        return dietChart;
    }

    public void setDietChart(DietChart dietChart) {
        this.dietChart = dietChart;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(String foodItems) {
        this.foodItems = foodItems;
    }

    public List<MealFood> getMealFoods() {
        return mealFoods;
    }

    public void setMealFoods(List<MealFood> mealFoods) {
        this.mealFoods = mealFoods;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public Double getFiber() {
        return fiber;
    }

    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }

    public String getRasa() {
        return rasa;
    }

    public void setRasa(String rasa) {
        this.rasa = rasa;
    }

    public String getVirya() {
        return virya;
    }

    public void setVirya(String virya) {
        this.virya = virya;
    }

    public String getVipaka() {
        return vipaka;
    }

    public void setVipaka(String vipaka) {
        this.vipaka = vipaka;
    }

    public String getDosha() {
        return dosha;
    }

    public void setDosha(String dosha) {
        this.dosha = dosha;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
}
