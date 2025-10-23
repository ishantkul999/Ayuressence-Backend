/*package com.ayurveda.diet.service;

import com.ayurveda.diet.dto.DietChartRequest;
import com.ayurveda.diet.model.*;
        import com.ayurveda.diet.repository.*;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class DietChartService {

    @Autowired
    private DietChartRepository dietChartRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DietitianRepository dietitianRepository;

    @Autowired
    private HealthParameterRepository healthParameterRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Transactional
    public DietChart generateDietChart(DietChartRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Dietitian dietitian = dietitianRepository.findById(request.getDietitianId())
                .orElseThrow(() -> new RuntimeException("Dietitian not found"));

        // Get latest health parameters
        List<HealthParameter> healthParams = healthParameterRepository
                .findByPatientIdOrderByDateDesc(request.getPatientId());

        // Create diet chart
        DietChart dietChart = new DietChart();
        dietChart.setPatient(patient);
        dietChart.setDietitian(dietitian);
        dietChart.setCreatedDate(LocalDate.now());
        dietChart.setStartDate(LocalDate.now());
        dietChart.setEndDate(LocalDate.now().plusDays(request.getDurationDays()));
        dietChart.setDurationDays(request.getDurationDays());
        dietChart.setStatus("ACTIVE");

        // Generate meals based on health parameters
        List<Meal> meals = generateMeals(dietChart, request.getDurationDays(), patient, healthParams);
        dietChart.setMeals(meals);

        // Calculate totals
        calculateNutritionalSummary(dietChart);

        return dietChartRepository.save(dietChart);
    }

    private List<Meal> generateMeals(DietChart dietChart, int days, Patient patient,
                                     List<HealthParameter> healthParams) {
        List<Meal> meals = new ArrayList<>();

        // Calculate daily calorie requirement
        double dailyCalories = calculateDailyCalories(patient, healthParams);

        String[] mealTypes = {"BREAKFAST", "LUNCH", "SNACK", "DINNER"};
        double[] mealCalorieRatio = {0.25, 0.35, 0.15, 0.25};

        for (int day = 1; day <= days; day++) {
            for (int i = 0; i < mealTypes.length; i++) {
                Meal meal = createMeal(dietChart, day, mealTypes[i],
                        dailyCalories * mealCalorieRatio[i], patient);
                meals.add(meal);
            }
        }

        return meals;
    }

    private Meal createMeal(DietChart dietChart, int dayNumber, String mealType,
                            double targetCalories, Patient patient) {
        Meal meal = new Meal();
        meal.setDietChart(dietChart);
        meal.setDayNumber(dayNumber);
        meal.setMealType(mealType);

        // Set timing
        meal.setTiming(getMealTiming(mealType));

        // Generate meal content based on type and patient needs
        Map<String, Object> mealContent = generateMealContent(mealType, targetCalories, patient);

        meal.setFoodItems((String) mealContent.get("items"));
        meal.setCalories((Double) mealContent.get("calories"));
        meal.setProtein((Double) mealContent.get("protein"));
        meal.setCarbs((Double) mealContent.get("carbs"));
        meal.setFats((Double) mealContent.get("fats"));
        meal.setFiber((Double) mealContent.get("fiber"));
        meal.setRasa((String) mealContent.get("rasa"));
        meal.setVirya((String) mealContent.get("virya"));
        meal.setVipaka((String) mealContent.get("vipaka"));
        meal.setDosha((String) mealContent.get("dosha"));
        meal.setBenefits((String) mealContent.get("benefits"));

        return meal;
    }

    private String getMealTiming(String mealType) {
        switch (mealType) {
            case "BREAKFAST": return "7:00 AM - 8:00 AM";
            case "LUNCH": return "12:00 PM - 1:00 PM";
            case "SNACK": return "4:00 PM - 5:00 PM";
            case "DINNER": return "7:00 PM - 8:00 PM";
            default: return "Flexible";
        }
    }

    private Map<String, Object> generateMealContent(String mealType, double targetCalories, Patient patient) {
        Map<String, Object> content = new HashMap<>();

        switch (mealType) {
            case "BREAKFAST":
                content.put("items", "Oats (50g), Banana (1 medium), Almonds (10), Honey (1 tsp)");
                content.put("calories", 350.0);
                content.put("protein", 12.0);
                content.put("carbs", 58.0);
                content.put("fats", 10.0);
                content.put("fiber", 8.0);
                content.put("rasa", "Sweet, Astringent");
                content.put("virya", "Cold");
                content.put("vipaka", "Sweet");
                content.put("dosha", "Balances Vata and Pitta");
                content.put("benefits", "Provides sustained energy, improves digestion, boosts immunity");
                break;

            case "LUNCH":
                content.put("items", "Brown Rice (1 cup), Moong Dal (1 cup), Mixed Vegetables (1 cup), Curd (1 small bowl)");
                content.put("calories", 500.0);
                content.put("protein", 18.0);
                content.put("carbs", 85.0);
                content.put("fats", 8.0);
                content.put("fiber", 12.0);
                content.put("rasa", "Sweet, Astringent");
                content.put("virya", "Cold to Neutral");
                content.put("vipaka", "Sweet");
                content.put("dosha", "Tridoshic (balances all three doshas)");
                content.put("benefits", "Complete nutrition, easy to digest, cooling effect");
                break;

            case "SNACK":
                content.put("items", "Sprouts (1 cup), Green Tea (1 cup), Dates (2-3)");
                content.put("calories", 180.0);
                content.put("protein", 8.0);
                content.put("carbs", 32.0);
                content.put("fats", 2.0);
                content.put("fiber", 6.0);
                content.put("rasa", "Sweet, Astringent");
                content.put("virya", "Neutral");
                content.put("vipaka", "Sweet");
                content.put("dosha", "Balances Vata");
                content.put("benefits", "Protein boost, antioxidants, sustained energy");
                break;

            case "DINNER":
                content.put("items", "Roti (2), Mixed Dal (1 cup), Spinach Sabzi (1 cup), Buttermilk (1 glass)");
                content.put("calories", 420.0);
                content.put("protein", 15.0);
                content.put("carbs", 65.0);
                content.put("fats", 12.0);
                content.put("fiber", 10.0);
                content.put("rasa", "Sweet, Astringent, Bitter");
                content.put("virya", "Neutral to Cold");
                content.put("vipaka", "Sweet");
                content.put("dosha", "Balances Pitta and Kapha");
                content.put("benefits", "Light on stomach, improves sleep, aids digestion");
                break;

            default:
                content.put("items", "Balanced meal");
                content.put("calories", targetCalories);
                content.put("protein", 15.0);
                content.put("carbs", 50.0);
                content.put("fats", 10.0);
                content.put("fiber", 8.0);
                content.put("rasa", "Balanced");
                content.put("virya", "Neutral");
                content.put("vipaka", "Sweet");
                content.put("dosha", "Tridoshic");
                content.put("benefits", "Balanced nutrition");
        }

        return content;
    }

    private double calculateDailyCalories(Patient patient, List<HealthParameter> healthParams) {
        // Basic BMR calculation (Mifflin-St Jeor Equation)
        double bmr;
        if ("MALE".equalsIgnoreCase(patient.getGender())) {
            bmr = 10 * patient.getWeight() + 6.25 * patient.getHeight() - 5 * patient.getAge() + 5;
        } else {
            bmr = 10 * patient.getWeight() + 6.25 * patient.getHeight() - 5 * patient.getAge() - 161;
        }

        // Activity factor (moderate activity)
        double dailyCalories = bmr * 1.55;

        // Adjust based on health goals
        if (patient.getBmi() != null) {
            if (patient.getBmi() < 18.5) {
                dailyCalories += 300; // Weight gain
            } else if (patient.getBmi() > 25) {
                dailyCalories -= 300; // Weight loss
            }
        }

        return Math.round(dailyCalories);
    }

    private void calculateNutritionalSummary(DietChart dietChart) {
        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFats = 0;
        double totalFiber = 0;

        for (Meal meal : dietChart.getMeals()) {
            totalCalories += meal.getCalories();
            totalProtein += meal.getProtein();
            totalCarbs += meal.getCarbs();
            totalFats += meal.getFats();
            totalFiber += meal.getFiber();
        }

        dietChart.setTotalCalories(Math.round(totalCalories * 100.0) / 100.0);
        dietChart.setTotalProtein(Math.round(totalProtein * 100.0) / 100.0);
        dietChart.setTotalCarbs(Math.round(totalCarbs * 100.0) / 100.0);
        dietChart.setTotalFats(Math.round(totalFats * 100.0) / 100.0);
        dietChart.setTotalFiber(Math.round(totalFiber * 100.0) / 100.0);
    }

}*/
package com.ayurveda.diet.service;

import com.ayurveda.diet.dto.*;
import com.ayurveda.diet.model.*;
import com.ayurveda.diet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DietChartService {

    @Autowired
    private DietChartRepository dietChartRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DietitianRepository dietitianRepository;

    @Autowired
    private HealthParameterRepository healthParameterRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private MealFoodRepository mealFoodRepository;

    @Transactional
    public DietChart generateDietChart(DietChartRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Dietitian dietitian = dietitianRepository.findById(request.getDietitianId())
                .orElseThrow(() -> new RuntimeException("Dietitian not found"));

        List<HealthParameter> healthParams = healthParameterRepository
                .findByPatientIdOrderByDateDesc(request.getPatientId());

        DietChart dietChart = new DietChart();
        dietChart.setPatient(patient);
        dietChart.setDietitian(dietitian);
        dietChart.setCreatedDate(LocalDate.now());
        dietChart.setStartDate(LocalDate.now());
        dietChart.setEndDate(LocalDate.now().plusDays(request.getDurationDays()));
        dietChart.setDurationDays(request.getDurationDays());
        dietChart.setStatus("ACTIVE");
        dietChart.setVersionNumber(1);
        dietChart.setLastModifiedDate(LocalDateTime.now());
        dietChart.setLastModifiedBy(dietitian.getName());

        List<Meal> meals = generateMeals(dietChart, request.getDurationDays(), patient, healthParams);
        dietChart.setMeals(meals);

        calculateNutritionalSummary(dietChart);

        return dietChartRepository.save(dietChart);
    }

    // NEW: Get diet chart with full details for dietitian
    @Transactional(readOnly = true)
    public DietChart getDietChartWithFullDetails(Long dietChartId) {
        return dietChartRepository.findById(dietChartId)
                .orElseThrow(() -> new RuntimeException("Diet Chart not found"));
    }

    // NEW: Get all diet charts for a patient
    @Transactional(readOnly = true)
    public List<DietChart> getPatientDietCharts(Long patientId) {
        return dietChartRepository.findByPatientIdOrderByCreatedDateDesc(patientId);
    }

    // NEW: Update entire meal with new food items
    @Transactional
    public DietChart updateMeal(Long dietChartId, Long mealId,
                                MealUpdateRequest request, Long dietitianId) {
        DietChart dietChart = dietChartRepository.findById(dietChartId)
                .orElseThrow(() -> new RuntimeException("Diet Chart not found"));

        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        if (!meal.getDietChart().getId().equals(dietChartId)) {
            throw new RuntimeException("Meal does not belong to this diet chart");
        }

        // Clear existing meal foods
        meal.getMealFoods().clear();
        mealFoodRepository.deleteByMealId(mealId);

        // Add new food items from database
        for (MealFoodRequest foodRequest : request.getMealFoods()) {
            addMealFoodFromDatabase(meal, foodRequest);
        }

        // Update timing if provided
        if (request.getTiming() != null) {
            meal.setTiming(request.getTiming());
        }

        // Recalculate meal totals
        meal.recalculateTotals();

        // Update diet chart version and modification info
        Dietitian dietitian = dietitianRepository.findById(dietitianId)
                .orElseThrow(() -> new RuntimeException("Dietitian not found"));

        dietChart.incrementVersion();
        dietChart.setLastModifiedBy(dietitian.getName());
        if (request.getModificationNotes() != null) {
            dietChart.setModificationNotes(request.getModificationNotes());
        }

        // Recalculate diet chart totals
        dietChart.recalculateTotals();

        mealRepository.save(meal);
        return dietChartRepository.save(dietChart);
    }

    // NEW: Add single food item to existing meal
    @Transactional
    public DietChart addFoodToMeal(Long dietChartId, Long mealId,
                                   MealFoodRequest request, Long dietitianId) {
        DietChart dietChart = dietChartRepository.findById(dietChartId)
                .orElseThrow(() -> new RuntimeException("Diet Chart not found"));

        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        if (!meal.getDietChart().getId().equals(dietChartId)) {
            throw new RuntimeException("Meal does not belong to this diet chart");
        }

        // Add food from database
        addMealFoodFromDatabase(meal, request);

        // Recalculate meal totals
        meal.recalculateTotals();

        // Update diet chart version
        Dietitian dietitian = dietitianRepository.findById(dietitianId)
                .orElseThrow(() -> new RuntimeException("Dietitian not found"));

        dietChart.incrementVersion();
        dietChart.setLastModifiedBy(dietitian.getName());
        dietChart.recalculateTotals();

        mealRepository.save(meal);
        return dietChartRepository.save(dietChart);
    }

    // NEW: Remove food item from meal
    @Transactional
    public DietChart removeFoodFromMeal(Long dietChartId, Long mealId,
                                        Long mealFoodId, Long dietitianId) {
        DietChart dietChart = dietChartRepository.findById(dietChartId)
                .orElseThrow(() -> new RuntimeException("Diet Chart not found"));

        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        MealFood mealFood = mealFoodRepository.findById(mealFoodId)
                .orElseThrow(() -> new RuntimeException("MealFood not found"));

        if (!meal.getDietChart().getId().equals(dietChartId)) {
            throw new RuntimeException("Meal does not belong to this diet chart");
        }

        // Remove the food
        meal.getMealFoods().remove(mealFood);
        mealFoodRepository.delete(mealFood);

        // Recalculate meal totals
        meal.recalculateTotals();

        // Update diet chart version
        Dietitian dietitian = dietitianRepository.findById(dietitianId)
                .orElseThrow(() -> new RuntimeException("Dietitian not found"));

        dietChart.incrementVersion();
        dietChart.setLastModifiedBy(dietitian.getName());
        dietChart.recalculateTotals();

        mealRepository.save(meal);
        return dietChartRepository.save(dietChart);
    }

    // NEW: Update quantity of food in meal
    @Transactional
    public DietChart updateFoodQuantity(Long dietChartId, Long mealId,
                                        Long mealFoodId, Double newQuantity, Long dietitianId) {
        DietChart dietChart = dietChartRepository.findById(dietChartId)
                .orElseThrow(() -> new RuntimeException("Diet Chart not found"));

        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        MealFood mealFood = mealFoodRepository.findById(mealFoodId)
                .orElseThrow(() -> new RuntimeException("MealFood not found"));

        if (!meal.getDietChart().getId().equals(dietChartId)) {
            throw new RuntimeException("Meal does not belong to this diet chart");
        }

        // Update quantity and recalculate nutritional values
        mealFood.setQuantity(newQuantity);
        Food food = mealFood.getFood();

        // Calculate nutritional values based on new quantity
        double factor = newQuantity / 100.0; // Food values are per 100g
        mealFood.setCalories(food.getCalories() * factor);
        mealFood.setProtein(food.getProtein() * factor);
        mealFood.setCarbs(food.getCarbs() * factor);
        mealFood.setFats(food.getFats() * factor);
        mealFood.setFiber(food.getFiber() * factor);

        mealFoodRepository.save(mealFood);

        // Recalculate meal totals
        meal.recalculateTotals();

        // Update diet chart version
        Dietitian dietitian = dietitianRepository.findById(dietitianId)
                .orElseThrow(() -> new RuntimeException("Dietitian not found"));

        dietChart.incrementVersion();
        dietChart.setLastModifiedBy(dietitian.getName());
        dietChart.recalculateTotals();

        mealRepository.save(meal);
        return dietChartRepository.save(dietChart);
    }

    // NEW: Get all foods from database with optional filters
    @Transactional(readOnly = true)
    public List<Food> getAvailableFoods(String category, String dosha) {
        if (category != null && dosha != null) {
            return foodRepository.findByCategoryAndDoshaContaining(category, dosha);
        } else if (category != null) {
            return foodRepository.findByCategory(category);
        } else if (dosha != null) {
            return foodRepository.findByDoshaContaining(dosha);
        }
        return foodRepository.findAll();
    }

    // NEW: Search foods by name
    @Transactional(readOnly = true)
    public List<Food> searchFoods(String query) {
        return foodRepository.findByNameContainingIgnoreCase(query);
    }

    // Helper method to add MealFood from database
    private void addMealFoodFromDatabase(Meal meal, MealFoodRequest request) {
        Food food = foodRepository.findById(request.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + request.getFoodId()));

        MealFood mealFood = new MealFood();
        mealFood.setMeal(meal);
        mealFood.setFood(food);
        mealFood.setQuantity(request.getQuantity());

        // Calculate nutritional values based on quantity
        double factor = request.getQuantity() / 100.0; // Food values are per 100g
        mealFood.setCalories(food.getCalories() * factor);
        mealFood.setProtein(food.getProtein() * factor);
        mealFood.setCarbs(food.getCarbs() * factor);
        mealFood.setFats(food.getFats() * factor);
        mealFood.setFiber(food.getFiber() * factor);

        meal.getMealFoods().add(mealFood);
    }

    // Existing methods below...

    private List<Meal> generateMeals(DietChart dietChart, int days, Patient patient,
                                     List<HealthParameter> healthParams) {
        List<Meal> meals = new ArrayList<>();
        double dailyCalories = calculateDailyCalories(patient, healthParams);

        String[] mealTypes = {"BREAKFAST", "LUNCH", "SNACK", "DINNER"};
        double[] mealCalorieRatio = {0.25, 0.35, 0.15, 0.25};

        for (int day = 1; day <= days; day++) {
            for (int i = 0; i < mealTypes.length; i++) {
                Meal meal = createMeal(dietChart, day, mealTypes[i],
                        dailyCalories * mealCalorieRatio[i], patient);
                meals.add(meal);
            }
        }

        return meals;
    }

    private Meal createMeal(DietChart dietChart, int dayNumber, String mealType,
                            double targetCalories, Patient patient) {
        Meal meal = new Meal();
        meal.setDietChart(dietChart);
        meal.setDayNumber(dayNumber);
        meal.setMealType(mealType);
        meal.setTiming(getMealTiming(mealType));

        Map<String, Object> mealContent = generateMealContent(mealType, targetCalories, patient);

        meal.setFoodItems((String) mealContent.get("items"));
        meal.setCalories((Double) mealContent.get("calories"));
        meal.setProtein((Double) mealContent.get("protein"));
        meal.setCarbs((Double) mealContent.get("carbs"));
        meal.setFats((Double) mealContent.get("fats"));
        meal.setFiber((Double) mealContent.get("fiber"));
        meal.setRasa((String) mealContent.get("rasa"));
        meal.setVirya((String) mealContent.get("virya"));
        meal.setVipaka((String) mealContent.get("vipaka"));
        meal.setDosha((String) mealContent.get("dosha"));
        meal.setBenefits((String) mealContent.get("benefits"));

        return meal;
    }

    private String getMealTiming(String mealType) {
        switch (mealType) {
            case "BREAKFAST": return "7:00 AM - 8:00 AM";
            case "LUNCH": return "12:00 PM - 1:00 PM";
            case "SNACK": return "4:00 PM - 5:00 PM";
            case "DINNER": return "7:00 PM - 8:00 PM";
            default: return "Flexible";
        }
    }

    private Map<String, Object> generateMealContent(String mealType, double targetCalories, Patient patient) {
        Map<String, Object> content = new HashMap<>();

        switch (mealType) {
            case "BREAKFAST":
                content.put("items", "Oats (50g), Banana (1 medium), Almonds (10), Honey (1 tsp)");
                content.put("calories", 350.0);
                content.put("protein", 12.0);
                content.put("carbs", 58.0);
                content.put("fats", 10.0);
                content.put("fiber", 8.0);
                content.put("rasa", "Sweet, Astringent");
                content.put("virya", "Cold");
                content.put("vipaka", "Sweet");
                content.put("dosha", "Balances Vata and Pitta");
                content.put("benefits", "Provides sustained energy, improves digestion, boosts immunity");
                break;

            case "LUNCH":
                content.put("items", "Brown Rice (1 cup), Moong Dal (1 cup), Mixed Vegetables (1 cup), Curd (1 small bowl)");
                content.put("calories", 500.0);
                content.put("protein", 18.0);
                content.put("carbs", 85.0);
                content.put("fats", 8.0);
                content.put("fiber", 12.0);
                content.put("rasa", "Sweet, Astringent");
                content.put("virya", "Cold to Neutral");
                content.put("vipaka", "Sweet");
                content.put("dosha", "Tridoshic (balances all three doshas)");
                content.put("benefits", "Complete nutrition, easy to digest, cooling effect");
                break;

            case "SNACK":
                content.put("items", "Sprouts (1 cup), Green Tea (1 cup), Dates (2-3)");
                content.put("calories", 180.0);
                content.put("protein", 8.0);
                content.put("carbs", 32.0);
                content.put("fats", 2.0);
                content.put("fiber", 6.0);
                content.put("rasa", "Sweet, Astringent");
                content.put("virya", "Neutral");
                content.put("vipaka", "Sweet");
                content.put("dosha", "Balances Vata");
                content.put("benefits", "Protein boost, antioxidants, sustained energy");
                break;

            case "DINNER":
                content.put("items", "Roti (2), Mixed Dal (1 cup), Spinach Sabzi (1 cup), Buttermilk (1 glass)");
                content.put("calories", 420.0);
                content.put("protein", 15.0);
                content.put("carbs", 65.0);
                content.put("fats", 12.0);
                content.put("fiber", 10.0);
                content.put("rasa", "Sweet, Astringent, Bitter");
                content.put("virya", "Neutral to Cold");
                content.put("vipaka", "Sweet");
                content.put("dosha", "Balances Pitta and Kapha");
                content.put("benefits", "Light on stomach, improves sleep, aids digestion");
                break;

            default:
                content.put("items", "Balanced meal");
                content.put("calories", targetCalories);
                content.put("protein", 15.0);
                content.put("carbs", 50.0);
                content.put("fats", 10.0);
                content.put("fiber", 8.0);
                content.put("rasa", "Balanced");
                content.put("virya", "Neutral");
                content.put("vipaka", "Sweet");
                content.put("dosha", "Tridoshic");
                content.put("benefits", "Balanced nutrition");
        }

        return content;
    }

    private double calculateDailyCalories(Patient patient, List<HealthParameter> healthParams) {
        double bmr;
        if ("MALE".equalsIgnoreCase(patient.getGender())) {
            bmr = 10 * patient.getWeight() + 6.25 * patient.getHeight() - 5 * patient.getAge() + 5;
        } else {
            bmr = 10 * patient.getWeight() + 6.25 * patient.getHeight() - 5 * patient.getAge() - 161;
        }

        double dailyCalories = bmr * 1.55;

        if (patient.getBmi() != null) {
            if (patient.getBmi() < 18.5) {
                dailyCalories += 300;
            } else if (patient.getBmi() > 25) {
                dailyCalories -= 300;
            }
        }

        return Math.round(dailyCalories);
    }

    private void calculateNutritionalSummary(DietChart dietChart) {
        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFats = 0;
        double totalFiber = 0;

        for (Meal meal : dietChart.getMeals()) {
            totalCalories += meal.getCalories();
            totalProtein += meal.getProtein();
            totalCarbs += meal.getCarbs();
            totalFats += meal.getFats();
            totalFiber += meal.getFiber();
        }

        dietChart.setTotalCalories(Math.round(totalCalories * 100.0) / 100.0);
        dietChart.setTotalProtein(Math.round(totalProtein * 100.0) / 100.0);
        dietChart.setTotalCarbs(Math.round(totalCarbs * 100.0) / 100.0);
        dietChart.setTotalFats(Math.round(totalFats * 100.0) / 100.0);
        dietChart.setTotalFiber(Math.round(totalFiber * 100.0) / 100.0);
    }
}


