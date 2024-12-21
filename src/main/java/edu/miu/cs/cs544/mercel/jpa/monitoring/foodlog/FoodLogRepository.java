package edu.miu.cs.cs544.mercel.jpa.monitoring.foodlog;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodLogRepository extends JpaRepository<FoodLog, Long> {
    // Find FoodLogs by userId
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<FoodLog> findByUserId(Long userId);

    List<FoodLog> findByUserIdAndMealType(Long userId, String mealType);



    List<FoodLog> findByCaloriesGreaterThan(@Param("calories") int calories);

}
