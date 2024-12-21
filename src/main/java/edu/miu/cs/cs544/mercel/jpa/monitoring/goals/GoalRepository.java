package edu.miu.cs.cs544.mercel.jpa.monitoring.goals;


import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Goal> findByUserId(Long userId); // Find all goals for a user
    //find all
    List<Goal> findAll();

}

