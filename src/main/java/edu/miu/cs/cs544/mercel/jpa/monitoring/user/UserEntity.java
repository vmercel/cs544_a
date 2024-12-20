package edu.miu.cs.cs544.mercel.jpa.monitoring.user;

import edu.miu.cs.cs544.mercel.jpa.monitoring.foodlog.FoodLog;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;  // Add username field
    private String name;
    private String email;
    private String password; // Store hashed password
    private String role; // e.g., PATIENT or DIETITIAN

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FoodLog> foodLogs;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<FoodLog> getFoodLogs() {
        return foodLogs;
    }

    public void setFoodLogs(List<FoodLog> foodLogs) {
        this.foodLogs = foodLogs;
    }
}
