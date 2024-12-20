package edu.miu.cs.cs544.mercel.jpa.monitoring.foodlog;

import edu.miu.cs.cs544.mercel.jpa.monitoring.jms.JmsProducer;
import edu.miu.cs.cs544.mercel.jpa.monitoring.user.UserEntity;
import edu.miu.cs.cs544.mercel.jpa.monitoring.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/api/food-logs")
@Tag(name = "Food Log API", description = "Endpoints for managing food logs")
public class FoodLogController {

    @Autowired
    private FoodLogRepository foodLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JmsProducer jmsProducer;

    @PostMapping("/save/{id}")
    @Operation(summary = "Create a food log", description = "Saves a new food log entry and sends it to a JMS queue.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Food log created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<FoodLog> createFoodLog(@PathVariable Long id, @RequestBody FoodLog foodLog) {
        // Find the user by id
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserEntity not found"));

        // Associate the foodLog with the found user
        foodLog.setUser(user);

        // Save the food log to the database
        FoodLog savedLog = foodLogRepository.save(foodLog);

        // Create a multi-line formatted message including User's name
        String message = String.format(
                "Info: FoodLog sent by %s\n" +
                        "--------------------------\n" +
                        "User ID: %d\n" +
                        "User Name: %s\n" +
                        "Meal Type: %s\n" +
                        "Calories: %d\n" +
                        "Food Item: %s\n" +
                        "Log Date: %s\n" +
                        "Nutrients: %s\n" +
                        "--------------------------",
                user.getName(),
                savedLog.getUser().getId(),
                savedLog.getUser().getName(),
                savedLog.getMealType(),
                savedLog.getCalories(),
                savedLog.getFoodItem(),
                savedLog.getLogDate(),
                savedLog.getNutrients()
        );

        // Send the message to the queue
        jmsProducer.sendMessage("diet_rec_dietitian_queue", message);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedLog); // Return the created FoodLog
    }

    @PostMapping
    @Operation(summary = "Get all food logs", description = "Fetches all food log entries from the database.")
    @ApiResponse(responseCode = "200", description = "List of food logs retrieved successfully")
    public ResponseEntity<List<FoodLog>> getAllFoodLogs() {
        List<FoodLog> foodLogs = foodLogRepository.findAll();
        return ResponseEntity.ok(foodLogs);
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Get food logs by user ID", description = "Fetches food logs for a specific user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of food logs retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No food logs found for the user")
    })
    public ResponseEntity<List<FoodLog>> getFoodLogsByUser(@PathVariable Long userId) {
        List<FoodLog> foodLogs = foodLogRepository.findByUserId(userId);
        if (foodLogs.isEmpty()) {
            return noContent().build();
        }
        return ResponseEntity.ok(foodLogs);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete food log", description = "Deletes a food log entry by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Food log deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Food log not found")
    })
    public ResponseEntity<Void> deleteFoodLog(@PathVariable Long id) {
        foodLogRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/{mealType}")
    @Operation(summary = "Get food logs by user ID and meal type", description = "Fetches food logs for a specific user and meal type.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of food logs retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No food logs found for the user and meal type")
    })
    public ResponseEntity<List<FoodLog>> getFoodLogsByUserAndMealType(@PathVariable Long userId, @PathVariable String mealType) {
        List<FoodLog> foodLogs = foodLogRepository.findByUserIdAndMealType(userId, mealType);
        if (foodLogs.isEmpty()) {
            return noContent().build();
        }
        return ResponseEntity.ok(foodLogs);
    }


    @GetMapping("/food-logs/calories/{calories}")
    public ResponseEntity<List<FoodLog>> getFoodLogsByCalories(@PathVariable int calories) {
        return ResponseEntity.ok(foodLogRepository.findByCaloriesGreaterThan(calories));
    }
}
