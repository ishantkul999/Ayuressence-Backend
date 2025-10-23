package com.ayurveda.diet.repository;

import com.ayurveda.diet.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/*@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByNameContainingIgnoreCase(String name);
}*/
/*@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByCategory(String category);
    List<Food> findByDoshaContaining(String dosha);
    List<Food> findByCategoryAndDoshaContaining(String category, String dosha);
    List<Food> findByNameContainingIgnoreCase(String name);
    List<Food> findByRasa(String rasa);
    List<Food> findByVirya(String virya);
}*/
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    // Search and filter methods
    List<Food> findByCategory(String category);
    List<Food> findByDoshaContaining(String dosha);
    List<Food> findByCategoryAndDoshaContaining(String category, String dosha);
    List<Food> findByNameContainingIgnoreCase(String name);
    List<Food> findByRasa(String rasa);
    List<Food> findByVirya(String virya);
    List<Food> findByVipaka(String vipaka);

    // Advanced queries
    @Query("SELECT f FROM Food f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(f.category) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Food> searchByNameOrCategory(@Param("searchTerm") String searchTerm);

    @Query("SELECT f FROM Food f WHERE f.calories BETWEEN :minCal AND :maxCal")
    List<Food> findByCalorieRange(@Param("minCal") Double minCal, @Param("maxCal") Double maxCal);

    @Query("SELECT f FROM Food f WHERE f.protein >= :minProtein ORDER BY f.protein DESC")
    List<Food> findHighProteinFoods(@Param("minProtein") Double minProtein);

    @Query("SELECT DISTINCT f.category FROM Food f ORDER BY f.category")
    List<String> findAllCategories();

    @Query("SELECT f FROM Food f WHERE f.digestibility = :digestibility")
    List<Food> findByDigestibility(@Param("digestibility") String digestibility);

    // Ayurvedic specific queries
    @Query("SELECT f FROM Food f WHERE f.dosha LIKE %:dosha% AND f.category = :category")
    List<Food> findByCategoryAndDosha(@Param("category") String category, @Param("dosha") String dosha);

    @Query("SELECT f FROM Food f WHERE f.virya = :virya AND f.rasa LIKE %:rasa%")
    List<Food> findByViryaAndRasa(@Param("virya") String virya, @Param("rasa") String rasa);
}