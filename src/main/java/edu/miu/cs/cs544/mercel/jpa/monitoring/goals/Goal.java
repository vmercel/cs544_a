package edu.miu.cs.cs544.mercel.jpa.monitoring.goals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.miu.cs.cs544.mercel.jpa.monitoring.user.UserEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goalName; // e.g., "Lose Weight", "Run 5K"
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isAchieved;

    @Version
    private int version; // Optimistic Locking Version Field

    @ManyToOne
    @JoinColumn(name = "user_id") // Links to UserEntity
    @JsonIgnore

    private UserEntity user;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isAchieved() {
        return isAchieved;
    }

    public void setAchieved(boolean achieved) {
        isAchieved = achieved;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}

