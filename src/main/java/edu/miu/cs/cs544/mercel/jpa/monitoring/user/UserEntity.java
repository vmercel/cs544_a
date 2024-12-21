package edu.miu.cs.cs544.mercel.jpa.monitoring.user;

import edu.miu.cs.cs544.mercel.jpa.monitoring.foodlog.FoodLog;
import edu.miu.cs.cs544.mercel.jpa.monitoring.vitals.Vitals;
import edu.miu.cs.cs544.mercel.jpa.monitoring.goals.Goal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Schema(description = "Represents a user in the system")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the user", example = "1", required = true)
    private Long id;

    @Schema(description = "Username of the user", example = "johndoe", required = true)
    private String username;

    @Schema(description = "Full name of the user", example = "John Doe", required = true)
    private String name;

    @Schema(description = "Email address of the user", example = "johndoe@example.com", required = true)
    private String email;

    @Schema(description = "Password for the user's account (hashed)", example = "encryptedPassword123", required = true)
    private String password;

    @Schema(description = "Role assigned to the user", example = "PATIENT", allowableValues = {"PATIENT", "DIETITIAN"})
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "List of food logs associated with the user")
    private List<FoodLog> foodLogs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "List of vitals recorded for the user")
    private List<Vitals> vitals;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "List of goals created by the user")
    private List<Goal> goals;

    @Version
    private int version; // Optimistic Locking Version Field

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

    public List<Vitals> getVitals() {
        return vitals;
    }

    public void setVitals(List<Vitals> vitals) {
        this.vitals = vitals;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
}
