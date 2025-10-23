package com.ayurveda.diet.service;


import com.ayurveda.diet.dto.LoginRequest;
import com.ayurveda.diet.dto.SignupRequest;
import com.ayurveda.diet.model.*;
        import com.ayurveda.diet.repository.*;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DietitianRepository dietitianRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Map<String, Object> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("email", user.getEmail());
        response.put("name", user.getName());
        response.put("role", user.getRole());

        if ("DIETITIAN".equals(user.getRole())) {
            Dietitian dietitian = dietitianRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Dietitian profile not found"));
            response.put("profileId", dietitian.getId());
        } else if ("PATIENT".equals(user.getRole())) {
            Patient patient = patientRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Patient profile not found"));
            response.put("profileId", patient.getId());
        }

        return response;
    }

    @Transactional
    public Map<String, Object> signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create User
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setName(request.getName());
        user = userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("email", user.getEmail());
        response.put("role", user.getRole());

        if ("DIETITIAN".equals(request.getRole())) {
            Dietitian dietitian = new Dietitian();
            dietitian.setUser(user);
            dietitian.setName(request.getName());
            dietitian.setEmail(request.getEmail());
            dietitian.setPhone(request.getPhone());
            dietitian.setQualification(request.getQualification());
            dietitian.setExperience(request.getExperience());
            dietitian.setSpecialization(request.getSpecialization());
            dietitian.setRating(4.5);
            dietitian.setTotalConsultations(0);
            dietitian = dietitianRepository.save(dietitian);
            response.put("profileId", dietitian.getId());
        } else if ("PATIENT".equals(request.getRole())) {
            Patient patient = new Patient();
            patient.setUser(user);
            patient.setName(request.getName());
            patient.setEmail(request.getEmail());
            patient.setAge(request.getAge());
            patient.setGender(request.getGender());
            patient.setWeight(request.getWeight());
            patient.setHeight(request.getHeight());
            patient.setHealthCondition(request.getHealthCondition());
            patient.setAllergies(request.getAllergies());

            // Calculate BMI
            if (request.getWeight() != null && request.getHeight() != null) {
                double heightInMeters = request.getHeight() / 100.0;
                double bmi = request.getWeight() / (heightInMeters * heightInMeters);
                patient.setBmi(Math.round(bmi * 100.0) / 100.0);
            }

            patient = patientRepository.save(patient);
            response.put("profileId", patient.getId());
        }

        return response;
    }
}