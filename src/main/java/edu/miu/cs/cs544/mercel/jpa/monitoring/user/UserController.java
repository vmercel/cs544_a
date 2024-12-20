package edu.miu.cs.cs544.mercel.jpa.monitoring.user;
import edu.miu.cs.cs544.mercel.jpa.monitoring.email.TMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TMailService emailService;
    // Get all users
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Create a user
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // create a new user
    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }



    @PostMapping("/send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        emailService.sendSimpleEmail(to, subject, body);
        return "Email sent successfully to " + to;
    }
    @GetMapping("/api/confirm")
    public String confirmAccount(@RequestParam("token") String token) {
        // Here, you would typically check if the token exists and is valid
        // For simplicity, we assume token validation is always successful.

        // Token verification logic would go here (e.g., store the token in the database, expire it after some time, etc.)
        // Assuming user account is activated successfully after confirmation

        return "Account confirmed successfully!";
    }

}

