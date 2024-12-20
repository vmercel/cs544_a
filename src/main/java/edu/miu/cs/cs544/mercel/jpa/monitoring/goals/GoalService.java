package edu.miu.cs.cs544.mercel.jpa.monitoring.goals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public List<Goal> getGoalsByUserId(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public Goal updateGoal(Long id, Goal updatedGoal) {
        Goal existingGoal = goalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Goal not found"));
        existingGoal.setGoalName(updatedGoal.getGoalName());
        existingGoal.setDescription(updatedGoal.getDescription());
        existingGoal.setStartDate(updatedGoal.getStartDate());
        existingGoal.setEndDate(updatedGoal.getEndDate());
        existingGoal.setAchieved(updatedGoal.isAchieved());
        return goalRepository.save(existingGoal);
    }

    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }

    // get all goals
    public List<Goal> getAllGoals(){
        return goalRepository.findAll();
    }
}
