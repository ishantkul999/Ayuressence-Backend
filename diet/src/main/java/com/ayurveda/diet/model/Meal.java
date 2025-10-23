package com.ayurveda.diet.model;


/*import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diet_chart_id")
    private DietChart dietChart;

    private Integer dayNumber;
    private String mealType; // BREAKFAST, LUNCH, SNACK, DINNER
    private String foodItems;
    private String timing;

    // Nutritional Info
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fats;
    private Double fiber;

    // Ayurvedic Properties
    private String rasa; // Taste
    private String virya; // Potency (Hot/Cold)
    private String vipaka; // Post-digestive effect
    private String dosha; // Vata, Pitta, Kapha
    private String benefits;
}*/

/*import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diet_chart_id")
    @JsonBackReference  // ADD THIS to prevent infinite loop
    private DietChart dietChart;

    private Integer dayNumber;
    private String mealType;
    private String foodItems;
    private String timing;

    // Nutritional Info
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fats;
    private Double fiber;

    // Ayurvedic Properties
    private String rasa;
    private String virya;
    private String vipaka;
    private String dosha;
    private String benefits;


}*/


/*import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(nullable = false)
    private Integer dayNumber;

    @Column(nullable = false)
    private String mealType; // BREAKFAST, LUNCH, SNACK, DINNER

    private String timing; // e.g., "7:00 AM - 8:00 AM"

    @Column(columnDefinition = "TEXT")
    private String foodItems; // Description of food items (legacy field)

    // NEW: Relationship with MealFood
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<MealFood> mealFoods = new ArrayList<>();

    // Nutritional Information (total for the meal)
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fats;
    private Double fiber;

    // Ayurvedic Properties
    private String rasa;
    private String virya;
    private String vipaka;
    private String dosha;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    // Constructors
    public Meal() {}

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
}*/


/*import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String mealType;
    private String timing;
    private String foodItems;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<MealFood> mealFoods = new ArrayList<>();

    private Double calories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDosha() {
        return dosha;
    }

    public void setDosha(String dosha) {
        this.dosha = dosha;
    }

    public Double getFats() {
        return fats;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getVipaka() {
        return vipaka;
    }

    public void setVipaka(String vipaka) {
        this.vipaka = vipaka;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public String getVirya() {
        return virya;
    }

    public void setVirya(String virya) {
        this.virya = virya;
    }

    public String getRasa() {
        return rasa;
    }

    public void setRasa(String rasa) {
        this.rasa = rasa;
    }

    public Double getFiber() {
        return fiber;
    }

    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    private Double protein;
    private Double carbs;
    private Double fats;

    public List<MealFood> getMealFoods() {
        return mealFoods;
    }

    public void setMealFoods(List<MealFood> mealFoods) {
        this.mealFoods = mealFoods;
    }

    public String getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(String foodItems) {
        this.foodItems = foodItems;
    }

    public String getMealType() {
        return mealType;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    private Double fiber;

    private String rasa;
    private String virya;

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public DietChart getDietChart() {
        return dietChart;
    }

    public void setDietChart(DietChart dietChart) {
        this.dietChart = dietChart;
    }

    private String vipaka;
    private String dosha;

    @Column(columnDefinition = "TEXT")
    private String benefits;
}*/
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
