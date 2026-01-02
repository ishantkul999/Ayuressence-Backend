package com.ayurveda.diet.repository;

import com.ayurveda.diet.model.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
