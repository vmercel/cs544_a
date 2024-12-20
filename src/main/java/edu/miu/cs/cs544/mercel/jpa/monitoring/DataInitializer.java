package edu.miu.cs.cs544.mercel.jpa.monitoring;

import edu.miu.cs.cs544.mercel.jpa.monitoring.foodlog.FoodLog;
import edu.miu.cs.cs544.mercel.jpa.monitoring.user.UserEntity;
import edu.miu.cs.cs544.mercel.jpa.monitoring.vitals.Vitals;
import edu.miu.cs.cs544.mercel.jpa.monitoring.foodlog.FoodLogRepository;
import edu.miu.cs.cs544.mercel.jpa.monitoring.user.UserRepository;
import edu.miu.cs.cs544.mercel.jpa.monitoring.vitals.VitalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodLogRepository foodLogRepository;

    @Autowired
    private VitalsRepository vitalsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create Users and assign usernames and passwords
        UserEntity patient = new UserEntity();
        patient.setUsername("user");  // Set username
        patient.setName("John Doe");
        patient.setEmail("john.doe@example.com");
        patient.setRole("USER");
        patient.setPassword(passwordEncoder.encode("user"));  // Encoding password
        userRepository.save(patient);

        UserEntity dietitian = new UserEntity();
        dietitian.setUsername("dietitian");  // Set username
        dietitian.setName("Jane Smith");
        dietitian.setEmail("jane.smith@example.com");
        dietitian.setRole("DIETITIAN");
        dietitian.setPassword(passwordEncoder.encode("dietitian"));
        userRepository.save(dietitian);

        UserEntity admin = new UserEntity();
        admin.setUsername("admin");  // Set username
        admin.setName("Admin UserEntity");
        admin.setEmail("admin@abc.com");
        admin.setRole("ADMIN");
        admin.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(admin);

        UserEntity guest = new UserEntity();
        guest.setUsername("guest");  // Set username
        guest.setName("Guest UserEntity");
        guest.setEmail("guest@example.com");
        guest.setRole("GUEST");
        guest.setPassword(passwordEncoder.encode("guest"));  // Encoding password
        userRepository.save(guest);

        // Create Food Logs for patient
        FoodLog breakfastLog = new FoodLog();
        breakfastLog.setMealType("Breakfast");
        breakfastLog.setFoodItem("Oatmeal");
        breakfastLog.setCalories(150);
        breakfastLog.setNutrients("Protein: 5g, Carbs: 27g, Fat: 2g");
        breakfastLog.setLogDate(LocalDate.now());
        breakfastLog.setUser(patient);  // Associate with the patient
        foodLogRepository.save(breakfastLog);

        FoodLog lunchLog = new FoodLog();
        lunchLog.setMealType("Lunch");
        lunchLog.setFoodItem("Grilled Chicken Salad");
        lunchLog.setCalories(350);
        lunchLog.setNutrients("Protein: 30g, Carbs: 15g, Fat: 10g");
        lunchLog.setLogDate(LocalDate.now());
        lunchLog.setUser(patient);  // Associate with the patient
        foodLogRepository.save(lunchLog);

        // Create Vitals for patient
        Vitals vitals = new Vitals();
        vitals.setHeartRate(72);
        vitals.setCaloriesBurned(500);
        vitals.setSteps(8000);
        vitals.setRecordDate(LocalDate.now());
        vitals.setUser(patient);  // Associate with the patient
        vitalsRepository.save(vitals);

        // Create Food Logs for dietitian (optional, can be role-specific if needed)
        FoodLog dietitianFoodLog = new FoodLog();
        dietitianFoodLog.setMealType("Snack");
        dietitianFoodLog.setFoodItem("Apple");
        dietitianFoodLog.setCalories(95);
        dietitianFoodLog.setNutrients("Protein: 0g, Carbs: 25g, Fat: 0g");
        dietitianFoodLog.setLogDate(LocalDate.now());
        dietitianFoodLog.setUser(dietitian);  // Associate with the dietitian
        foodLogRepository.save(dietitianFoodLog);

        // Optionally add vitals for dietitian
        Vitals dietitianVitals = new Vitals();
        dietitianVitals.setHeartRate(70);
        dietitianVitals.setCaloriesBurned(300);
        dietitianVitals.setSteps(10000);
        dietitianVitals.setRecordDate(LocalDate.now());
        dietitianVitals.setUser(dietitian);  // Associate with the dietitian
        vitalsRepository.save(dietitianVitals);
    }
}
