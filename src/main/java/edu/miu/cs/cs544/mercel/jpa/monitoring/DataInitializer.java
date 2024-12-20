package edu.miu.cs.cs544.mercel.jpa.monitoring;

import edu.miu.cs.cs544.mercel.jpa.monitoring.foodlog.FoodLog;
import edu.miu.cs.cs544.mercel.jpa.monitoring.goals.Goal;
import edu.miu.cs.cs544.mercel.jpa.monitoring.user.UserEntity;
import edu.miu.cs.cs544.mercel.jpa.monitoring.vitals.Vitals;
import edu.miu.cs.cs544.mercel.jpa.monitoring.foodlog.FoodLogRepository;
import edu.miu.cs.cs544.mercel.jpa.monitoring.goals.GoalRepository;
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
    private GoalRepository goalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create Users
        System.out.println("STARTED DATA INITIALIZATION.");
        UserEntity admin = new UserEntity();
        admin.setUsername("admin");
        admin.setName("MERCEL VUBANGSI");
        admin.setEmail("vmercel@miu.edu");
        admin.setRole("ADMIN");
        admin.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(admin);


        UserEntity patient = new UserEntity();
        patient.setUsername("user");
        patient.setName("John Doe");
        patient.setEmail("john.doe@example.com");
        patient.setRole("USER");
        patient.setPassword(passwordEncoder.encode("user"));
        userRepository.save(patient);

        UserEntity dietitian = new UserEntity();
        dietitian.setUsername("dietitian");
        dietitian.setName("Jane Smith");
        dietitian.setEmail("jane.smith@example.com");
        dietitian.setRole("DIETITIAN");
        dietitian.setPassword(passwordEncoder.encode("dietitian"));
        userRepository.save(dietitian);

        // Create Food Logs for patient
        FoodLog breakfastLog = new FoodLog();
        breakfastLog.setMealType("Breakfast");
        breakfastLog.setFoodItem("Oatmeal");
        breakfastLog.setCalories(150);
        breakfastLog.setNutrients("Protein: 5g, Carbs: 27g, Fat: 2g");
        breakfastLog.setLogDate(LocalDate.now());
        breakfastLog.setUser(patient);
        foodLogRepository.save(breakfastLog);

        FoodLog lunchLog = new FoodLog();
        lunchLog.setMealType("Lunch");
        lunchLog.setFoodItem("Grilled Chicken Salad");
        lunchLog.setCalories(350);
        lunchLog.setNutrients("Protein: 30g, Carbs: 15g, Fat: 10g");
        lunchLog.setLogDate(LocalDate.now());
        lunchLog.setUser(patient);
        foodLogRepository.save(lunchLog);

        // Create Vitals for patient
        Vitals vitals = new Vitals();
        vitals.setHeartRate(72);
        vitals.setCaloriesBurned(500);
        vitals.setSteps(8000);
        vitals.setRecordDate(LocalDate.now());
        vitals.setUser(patient);
        vitalsRepository.save(vitals);

        // Create Goals for patient
        Goal weightLossGoal = new Goal();
        weightLossGoal.setGoalName("Lose 5kg");
        weightLossGoal.setDescription("Achieve a weight loss of 5kg in 3 months through diet and exercise.");
        weightLossGoal.setStartDate(LocalDate.now());
        weightLossGoal.setEndDate(LocalDate.now().plusMonths(3));
        weightLossGoal.setAchieved(false);
        weightLossGoal.setUser(patient);
        goalRepository.save(weightLossGoal);

        Goal stepsGoal = new Goal();
        stepsGoal.setGoalName("10k Steps Daily");
        stepsGoal.setDescription("Walk 10,000 steps daily for overall health improvement.");
        stepsGoal.setStartDate(LocalDate.now());
        stepsGoal.setEndDate(LocalDate.now().plusMonths(1));
        stepsGoal.setAchieved(false);
        stepsGoal.setUser(patient);
        goalRepository.save(stepsGoal);

        // Optionally add goals for dietitian
        Goal dietitianGoal = new Goal();
        dietitianGoal.setGoalName("Increase Client Engagement");
        dietitianGoal.setDescription("Engage with 10 new clients in a month.");
        dietitianGoal.setStartDate(LocalDate.now());
        dietitianGoal.setEndDate(LocalDate.now().plusMonths(1));
        dietitianGoal.setAchieved(false);
        dietitianGoal.setUser(dietitian);
        goalRepository.save(dietitianGoal);

        System.out.println("FINISHED DATA INITIALIZATION.");
    }
}
