package com.ayurveda.diet.repository;

import com.ayurveda.diet.model.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*package com.ayurveda.diet.repository;

import com.ayurveda.diet.model.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealFoodRepository extends JpaRepository<MealFood, Long> {

    // Find all food items for a specific meal
    List<MealFood> findByMealId(Long mealId);

    // Delete a specific food item from a meal by its id
    void deleteByMealIdAndId(Long mealId, Long id);

    // Find food item in a meal by meal id and food id (for existence check or updating quantity)
    Optional<MealFood> findByMealIdAndFoodId(Long mealId, Long foodId);

    // Find all meals containing a specific food id (optional)
    List<MealFood> findByFoodId(Long foodId);

    // Delete all food items for a specific meal (optional)
    void deleteByMealId(Long mealId);

    // Check if a food exists in a meal (useful to avoid duplicates)
    boolean existsByMealIdAndFoodId(Long mealId, Long foodId);

    // Count how many food items a meal has (optional)
    Long countByMealId(Long mealId);
}
*/
@Repository
public interface MealFoodRepository extends JpaRepository<MealFood, Long> {
    List<MealFood> findByMealId(Long mealId);

    @Modifying
    @Query("DELETE FROM MealFood mf WHERE mf.meal.id = :mealId")
    void deleteByMealId(@Param("mealId") Long mealId);

    @Query("SELECT mf FROM MealFood mf WHERE mf.meal.dietChart.id = :dietChartId")
    List<MealFood> findByDietChartId(@Param("dietChartId") Long dietChartId);

    @Query("SELECT COUNT(mf) FROM MealFood mf WHERE mf.meal.id = :mealId")
    Long countByMealId(@Param("mealId") Long mealId);
}