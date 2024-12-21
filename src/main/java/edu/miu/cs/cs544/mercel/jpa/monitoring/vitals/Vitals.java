package edu.miu.cs.cs544.mercel.jpa.monitoring.vitals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.miu.cs.cs544.mercel.jpa.monitoring.user.UserEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Vitals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double heartRate;
    private int caloriesBurned;
    private int steps;
    private LocalDate recordDate;

    @Version
    private int version; // Optimistic Locking Version Field

    @ManyToOne
    @JoinColumn(name = "user_id") // Creates a foreign key column in the Vitals table
    @JsonIgnore
    private UserEntity user;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(double heartRate) {
        this.heartRate = heartRate;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
