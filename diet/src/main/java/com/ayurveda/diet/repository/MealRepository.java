package com.ayurveda.diet.repository;



import com.ayurveda.diet.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {


    List<Meal> findByDietChartId(Long dietChartId);

    List<Meal> findByDietChartIdAndDayNumber(Long dietChartId, int dayNumber);


    List<Meal> findByMealType(String mealType);


    List<Meal> findByDietChartIdAndMealType(Long dietChartId, String mealType);


    List<Meal> findByDoshaContaining(String dosha);


    @Query("SELECT m FROM Meal m WHERE m.calories BETWEEN :minCalories AND :maxCalories")
    List<Meal> findByCalorieRange(@Param("minCalories") int minCalories,
                                  @Param("maxCalories") int maxCalories);


    @Query("SELECT m FROM Meal m WHERE m.protein > :minProtein")
    List<Meal> findHighProteinMeals(@Param("minProtein") double minProtein);


    @Query("SELECT m FROM Meal m WHERE m.dietChart.id = :dietChartId " +
            "ORDER BY m.dayNumber ASC, " +
            "CASE m.mealType " +
            "WHEN 'BREAKFAST' THEN 1 " +
            "WHEN 'LUNCH' THEN 2 " +
            "WHEN 'SNACK' THEN 3 " +
            "WHEN 'DINNER' THEN 4 " +
            "END")
    List<Meal> findByDietChartIdOrderedByDayAndMealType(@Param("dietChartId") Long dietChartId);

    @Query("SELECT SUM(m.calories) FROM Meal m WHERE m.dietChart.id = :dietChartId")
    Double getTotalCaloriesByDietChartId(@Param("dietChartId") Long dietChartId);


    @Query("SELECT SUM(m.protein) FROM Meal m WHERE m.dietChart.id = :dietChartId")
    Double getTotalProteinByDietChartId(@Param("dietChartId") Long dietChartId);


    @Query("SELECT m FROM Meal m WHERE m.dietChart.id = :dietChartId " +
            "AND m.dayNumber BETWEEN :startDay AND :endDay")
    List<Meal> findByDietChartIdAndDayRange(@Param("dietChartId") Long dietChartId,
                                            @Param("startDay") int startDay,
                                            @Param("endDay") int endDay);


    List<Meal> findByRasaContaining(String rasa);


    List<Meal> findByVirya(String virya);


    @Query("SELECT COUNT(m) FROM Meal m WHERE m.dietChart.id = :dietChartId")
    Long countMealsByDietChartId(@Param("dietChartId") Long dietChartId);


    @Query("SELECT m FROM Meal m WHERE m.dietChart.patient.id = :patientId " +
            "ORDER BY m.dietChart.createdDate DESC, m.dayNumber ASC")
    List<Meal> findByPatientId(@Param("patientId") Long patientId);


    @Query("SELECT AVG(m.calories) FROM Meal m WHERE m.dietChart.id = :dietChartId")
    Double getAverageCaloriesByDietChartId(@Param("dietChartId") Long dietChartId);


    @Query("SELECT m FROM Meal m WHERE LOWER(m.foodItems) LIKE LOWER(CONCAT('%', :foodItem, '%'))")
    List<Meal> findByFoodItemsContaining(@Param("foodItem") String foodItem);


    void deleteByDietChartId(Long dietChartId);


    boolean existsByDietChartIdAndDayNumberAndMealType(Long dietChartId, int dayNumber, String mealType);


    List<Meal> findByVipaka(String vipaka);


    @Query("SELECT NEW map(" +
            "SUM(m.calories) as totalCalories, " +
            "SUM(m.protein) as totalProtein, " +
            "SUM(m.carbs) as totalCarbs, " +
            "SUM(m.fats) as totalFats, " +
            "SUM(m.fiber) as totalFiber) " +
            "FROM Meal m WHERE m.dietChart.id = :dietChartId AND m.dayNumber = :dayNumber")
    Optional<Object> getNutritionalSummaryForDay(@Param("dietChartId") Long dietChartId,
                                                 @Param("dayNumber") int dayNumber);

    @Query("SELECT m FROM Meal m WHERE m.fiber >= :minFiber ORDER BY m.fiber DESC")
    List<Meal> findHighFiberMeals(@Param("minFiber") double minFiber);
}
/*package com.ayurveda.diet.repository;

import com.ayurveda.diet.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByDietChartId(Long dietChartId);

    List<Meal> findByDietChartIdAndDayNumber(Long dietChartId, int dayNumber);

    List<Meal> findByMealType(String mealType);

    List<Meal> findByDietChartIdAndMealType(Long dietChartId, String mealType);

    List<Meal> findByDoshaContaining(String dosha);

    @Query("SELECT m FROM Meal m WHERE m.calories BETWEEN :minCalories AND :maxCalories")
    List<Meal> findByCalorieRange(@Param("minCalories") int minCalories,
                                  @Param("maxCalories") int maxCalories);

    @Query("SELECT m FROM Meal m WHERE m.protein > :minProtein")
    List<Meal> findHighProteinMeals(@Param("minProtein") double minProtein);

    @Query("SELECT m FROM Meal m WHERE m.dietChart.id = :dietChartId " +
            "ORDER BY m.dayNumber ASC, " +
            "CASE m.mealType " +
            "WHEN 'BREAKFAST' THEN 1 " +
            "WHEN 'LUNCH' THEN 2 " +
            "WHEN 'SNACK' THEN 3 " +
            "WHEN 'DINNER' THEN 4 " +
            "END")
    List<Meal> findByDietChartIdOrderedByDayAndMealType(@Param("dietChartId") Long dietChartId);

    @Query("SELECT SUM(m.calories) FROM Meal m WHERE m.dietChart.id = :dietChartId")
    Double getTotalCaloriesByDietChartId(@Param("dietChartId") Long dietChartId);

    @Query("SELECT SUM(m.protein) FROM Meal m WHERE m.dietChart.id = :dietChartId")
    Double getTotalProteinByDietChartId(@Param("dietChartId") Long dietChartId);

    @Query("SELECT m FROM Meal m WHERE m.dietChart.id = :dietChartId " +
            "AND m.dayNumber BETWEEN :startDay AND :endDay")
    List<Meal> findByDietChartIdAndDayRange(@Param("dietChartId") Long dietChartId,
                                            @Param("startDay") int startDay,
                                            @Param("endDay") int endDay);

    List<Meal> findByRasaContaining(String rasa);

    List<Meal> findByVirya(String virya);

    @Query("SELECT COUNT(m) FROM Meal m WHERE m.dietChart.id = :dietChartId")
    Long countMealsByDietChartId(@Param("dietChartId") Long dietChartId);

    @Query("SELECT m FROM Meal m WHERE m.dietChart.patient.id = :patientId " +
            "ORDER BY m.dietChart.createdDate DESC, m.dayNumber ASC")
    List<Meal> findByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT AVG(m.calories) FROM Meal m WHERE m.dietChart.id = :dietChartId")
    Double getAverageCaloriesByDietChartId(@Param("dietChartId") Long dietChartId);

    @Query("SELECT m FROM Meal m WHERE LOWER(m.foodItems) LIKE LOWER(CONCAT('%', :foodItem, '%'))")
    List<Meal> findByFoodItemsContaining(@Param("foodItem") String foodItem);

    void deleteByDietChartId(Long dietChartId);

    boolean existsByDietChartIdAndDayNumberAndMealType(Long dietChartId, int dayNumber, String mealType);

    List<Meal> findByVipaka(String vipaka);

    @Query("SELECT NEW map(" +
            "SUM(m.calories) as totalCalories, " +
            "SUM(m.protein) as totalProtein, " +
            "SUM(m.carbs) as totalCarbs, " +
            "SUM(m.fats) as totalFats, " +
            "SUM(m.fiber) as totalFiber) " +
            "FROM Meal m WHERE m.dietChart.id = :dietChartId AND m.dayNumber = :dayNumber")
    Optional<Object> getNutritionalSummaryForDay(@Param("dietChartId") Long dietChartId,
                                                 @Param("dayNumber") int dayNumber);

    @Query("SELECT m FROM Meal m WHERE m.fiber >= :minFiber ORDER BY m.fiber DESC")
    List<Meal> findHighFiberMeals(@Param("minFiber") double minFiber);

    // NEW: Fetch meal with foods eagerly loaded for detailed editing and viewing
    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.foods WHERE m.id = :mealId")
    Optional<Meal> findDetailedById(@Param("mealId") Long mealId);
}
*/