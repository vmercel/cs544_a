package edu.miu.cs.cs544.mercel.jpa.monitoring.foodlog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.miu.cs.cs544.mercel.jpa.monitoring.user.UserEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class FoodLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mealType; // Breakfast, Lunch, Dinner
    private String foodItem;
    private int calories;
    private String nutrients; // Example: "Protein: 10g, Carbs: 30g"
    private LocalDate logDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // This breaks the circular reference
    private UserEntity user;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getNutrients() {
        return nutrients;
    }

    public void setNutrients(String nutrients) {
        this.nutrients = nutrients;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
