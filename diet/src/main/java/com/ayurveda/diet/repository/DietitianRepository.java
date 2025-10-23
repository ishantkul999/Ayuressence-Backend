package com.ayurveda.diet.repository;



import com.ayurveda.diet.model.Dietitian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DietitianRepository extends JpaRepository<Dietitian, Long> {
    Optional<Dietitian> findByUserId(Long userId);
}