package edu.miu.cs.cs544.mercel.jpa.monitoring.goals;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUserId(Long userId); // Find all goals for a user
    //find all
    List<Goal> findAll();

}

