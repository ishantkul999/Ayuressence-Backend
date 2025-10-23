package com.ayurveda.diet.config;

import com.ayurveda.diet.model.Food;
import com.ayurveda.diet.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public void run(String... args) {
        initializeFoods();
    }

    private void initializeFoods() {
        if (foodRepository.count() > 0) {
            return; // Data already initialized
        }

        // Grains
        foodRepository.save(createFood("Rice (White)", "Grain", 130, 2.7, 28, 0.3, 0.4,
                10, 0.8, 0, 0, "Sweet", "Cold", "Sweet", "Kapha+", "Easy",
                "Quick energy, easy to digest, cooling"));

        foodRepository.save(createFood("Brown Rice", "Grain", 112, 2.6, 24, 0.9, 1.8,
                10, 0.5, 0, 0, "Sweet", "Neutral", "Sweet", "Tridoshic", "Moderate",
                "High fiber, sustained energy, nutrient-rich"));

        foodRepository.save(createFood("Wheat Roti", "Grain", 71, 3, 15, 0.4, 2,
                8, 0.9, 0, 0, "Sweet", "Cold", "Sweet", "Kapha+", "Moderate",
                "Good for strength, nutritious"));

        foodRepository.save(createFood("Oats", "Grain", 389, 17, 66, 7, 11,
                54, 5, 0, 0, "Sweet, Astringent", "Cold", "Sweet", "Vata-, Pitta-", "Easy",
                "Lowers cholesterol, heart-healthy, sustained energy"));

        // Lentils/Pulses
        foodRepository.save(createFood("Moong Dal", "Pulse", 347, 24, 63, 1.2, 16,
                132, 6.7, 4.8, 114, "Sweet, Astringent", "Cold", "Sweet", "Tridoshic", "Easy",
                "Easily digestible, detoxifying, protein-rich"));

        foodRepository.save(createFood("Toor Dal", "Pulse", 335, 22, 62, 1.5, 15,
                130, 5.8, 0, 0, "Sweet, Astringent", "Neutral", "Sweet", "Tridoshic", "Moderate",
                "Protein-rich, energizing, nutritious"));

        foodRepository.save(createFood("Chickpeas", "Pulse", 364, 19, 61, 6, 17,
                105, 6.2, 4, 67, "Sweet, Astringent", "Neutral", "Sweet", "Vata+, Kapha-", "Heavy",
                "High protein, fiber-rich, sustaining"));

        // Vegetables
        foodRepository.save(createFood("Spinach", "Vegetable", 23, 2.9, 3.6, 0.4, 2.2,
                99, 2.7, 28, 469, "Astringent, Sweet", "Cold", "Pungent", "Pitta-, Kapha-", "Easy",
                "Iron-rich, improves blood quality, cooling"));

        foodRepository.save(createFood("Carrot", "Vegetable", 41, 0.9, 10, 0.2, 2.8,
                33, 0.3, 5.9, 835, "Sweet", "Neutral", "Sweet", "Tridoshic", "Easy",
                "Good for eyes, improves digestion, immunity"));

        foodRepository.save(createFood("Tomato", "Vegetable", 18, 0.9, 3.9, 0.2, 1.2,
                10, 0.3, 13.7, 42, "Sour, Sweet", "Hot", "Sour", "Pitta+, Kapha-", "Easy",
                "Antioxidant-rich, vitamin C, lycopene"));

        foodRepository.save(createFood("Broccoli", "Vegetable", 34, 2.8, 7, 0.4, 2.6,
                47, 0.7, 89, 31, "Bitter, Astringent", "Cold", "Pungent", "Kapha-, Pitta-", "Moderate",
                "Cancer-fighting, detoxifying, vitamin-rich"));

        foodRepository.save(createFood("Bottle Gourd", "Vegetable", 14, 0.6, 3.4, 0.0, 0.5,
                26, 0.2, 10, 16, "Sweet, Bitter", "Cold", "Sweet", "Tridoshic", "Easy",
                "Cooling, hydrating, calming, easy to digest"));

        // Fruits
        foodRepository.save(createFood("Banana", "Fruit", 89, 1.1, 23, 0.3, 2.6,
                5, 0.3, 8.7, 64, "Sweet", "Cold", "Sour", "Vata-, Pitta-, Kapha+", "Easy",
                "Quick energy, potassium-rich, strengthening"));

        foodRepository.save(createFood("Apple", "Fruit", 52, 0.3, 14, 0.2, 2.4,
                6, 0.1, 4.6, 54, "Sweet, Astringent", "Cold", "Sweet", "Tridoshic", "Easy",
                "Cleansing, cooling, good for heart"));

        foodRepository.save(createFood("Mango", "Fruit", 60, 0.8, 15, 0.4, 1.6,
                11, 0.2, 36, 54, "Sweet, Sour", "Hot", "Sweet", "Pitta+, Vata-, Kapha+", "Easy",
                "Nutritious, energizing, immune-boosting"));

        foodRepository.save(createFood("Pomegranate", "Fruit", 83, 1.7, 19, 1.2, 4,
                10, 0.3, 10, 0, "Sweet, Astringent, Sour", "Cold", "Sweet", "Tridoshic", "Easy",
                "Blood-building, antioxidant-rich, cooling"));

        // Dairy
        foodRepository.save(createFood("Cow Milk", "Dairy", 61, 3.2, 4.8, 3.3, 0,
                113, 0.0, 0, 46, "Sweet", "Cold", "Sweet", "Vata-, Pitta-, Kapha+", "Moderate",
                "Nourishing, strengthening, calming"));

        foodRepository.save(createFood("Curd/Yogurt", "Dairy", 60, 3.5, 4.7, 3.3, 0,
                121, 0.1, 0.5, 27, "Sweet, Sour", "Hot", "Sour", "Vata-, Pitta+, Kapha+", "Moderate",
                "Probiotic, aids digestion, cooling in moderate amounts"));

        foodRepository.save(createFood("Paneer", "Dairy", 265, 18.3, 1.2, 20.8, 0,
                480, 0.2, 0, 78, "Sweet", "Cold", "Sweet", "Vata-, Kapha+", "Heavy",
                "High protein, calcium-rich, strengthening"));

        // Nuts and Seeds
        foodRepository.save(createFood("Almonds", "Nut", 579, 21, 22, 50, 12.5,
                269, 3.7, 0, 1, "Sweet", "Hot", "Sweet", "Vata-, Pitta+, Kapha+", "Heavy",
                "Brain food, heart-healthy, energy-boosting"));

        foodRepository.save(createFood("Walnuts", "Nut", 654, 15, 14, 65, 6.7,
                98, 2.9, 1.3, 20, "Sweet, Astringent", "Hot", "Sweet", "Vata-", "Heavy",
                "Omega-3 rich, brain health, anti-inflammatory"));

        foodRepository.save(createFood("Sesame Seeds", "Seed", 573, 18, 23, 50, 12,
                975, 14.6, 0, 9, "Sweet, Bitter", "Hot", "Sweet", "Vata-, Kapha+", "Heavy",
                "Calcium-rich, warming, strengthening"));

        // Spices/Herbs
        foodRepository.save(createFood("Ginger", "Spice", 80, 1.8, 18, 0.8, 2,
                16, 0.6, 5, 0, "Pungent, Sweet", "Hot", "Sweet", "Vata-, Kapha-", "Easy",
                "Digestive, anti-inflammatory, warming"));

        foodRepository.save(createFood("Turmeric", "Spice", 354, 8, 65, 10, 21,
                183, 41.4, 25.9, 0, "Bitter, Pungent, Astringent", "Hot", "Pungent", "Tridoshic", "Easy",
                "Anti-inflammatory, healing, blood purifier"));

        foodRepository.save(createFood("Cumin", "Spice", 375, 18, 44, 22, 11,
                931, 66.4, 7.7, 64, "Pungent, Bitter", "Cold", "Pungent", "Tridoshic", "Easy",
                "Digestive, cooling, detoxifying"));

        // Others
        foodRepository.save(createFood("Honey", "Sweetener", 304, 0.3, 82, 0, 0.2,
                6, 0.4, 0.5, 0, "Sweet", "Hot", "Sweet", "Kapha+, Vata-, Pitta+", "Easy",
                "Natural sweetener, healing, energy-boosting"));

        foodRepository.save(createFood("Ghee", "Fat", 900, 0, 0, 100, 0,
                4, 0, 0, 840, "Sweet", "Cold", "Sweet", "Vata-, Pitta-", "Moderate",
                "Lubricating, nourishing, improves digestion"));

        System.out.println("Food database initialized with " + foodRepository.count() + " items");
    }

    private Food createFood(String name, String category, double calories, double protein,
                            double carbs, double fats, double fiber, double calcium,
                            double iron, double vitaminC, double vitaminA,
                            String rasa, String virya, String vipaka, String dosha,
                            String digestibility, String benefits) {
        Food food = new Food();
        food.setName(name);
        food.setCategory(category);
        food.setCalories(calories);
        food.setProtein(protein);
        food.setCarbs(carbs);
        food.setFats(fats);
        food.setFiber(fiber);
        food.setCalcium(calcium);
        food.setIron(iron);
        food.setVitaminC(vitaminC);
        food.setVitaminA(vitaminA);
        food.setRasa(rasa);
        food.setVirya(virya);
        food.setVipaka(vipaka);
        food.setDosha(dosha);
        food.setDigestibility(digestibility);
        food.setBenefits(benefits);
        return food;
    }
}