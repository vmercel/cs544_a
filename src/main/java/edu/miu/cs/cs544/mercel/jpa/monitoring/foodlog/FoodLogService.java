package edu.miu.cs.cs544.mercel.jpa.monitoring.foodlog;

import edu.miu.cs.cs544.mercel.jpa.monitoring.jms.JmsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodLogService {

    @Autowired
    private FoodLogRepository foodLogRepository;

    @Autowired
    private JmsProducer jmsProducer;

    public FoodLog createFoodLog(FoodLog foodLog) {
        FoodLog savedLog = foodLogRepository.save(foodLog);

        // Convert the food log to a message (e.g., JSON string)
        String message = String.format(
                "{\"userId\": %d, \"mealType\": \"%s\", \"calories\": %d, \"foodItem\": \"%s\"}",
                savedLog.getUser().getId(), savedLog.getMealType(), savedLog.getCalories(), savedLog.getFoodItem()
        );

        // Send the message to the queue
        jmsProducer.sendMessage("diet_rec_dietitian_queue", message);

        return savedLog;
    }

    public List<FoodLog> getAllFoodLogs() {
        return foodLogRepository.findAll();
    }

    public List<FoodLog> getFoodLogsByUserId(Long userId) {
        return foodLogRepository.findByUserId(userId);
    }
}

