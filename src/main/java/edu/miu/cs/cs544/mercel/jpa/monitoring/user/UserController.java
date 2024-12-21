package edu.miu.cs.cs544.mercel.jpa.monitoring.user;

import edu.miu.cs.cs544.mercel.jpa.monitoring.email.TMailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TMailService emailService;

    @Operation(summary = "Retrieve all users", description = "Fetches all users from the system.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users")
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Create a new user", description = "Adds a new user to the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided")
    })
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by their unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(
            @Parameter(description = "ID of the user to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Update user", description = "Updates an existing user's information.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(
            @Parameter(description = "ID of the user to update") @PathVariable Long id,
            @RequestBody UserEntity updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @Operation(summary = "Delete user", description = "Removes a user from the system by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Register a new user", description = "Registers a new user and sends a confirmation email.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid registration data provided")
    })
    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @Operation(summary = "Send an email", description = "Sends a simple email to the specified recipient.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid email details provided")
    })
    @PostMapping("/send")
    public String sendEmail(
            @Parameter(description = "Recipient email address") @RequestParam String to,
            @Parameter(description = "Subject of the email") @RequestParam String subject,
            @Parameter(description = "Body of the email") @RequestParam String body) {
        emailService.sendSimpleEmail(to, subject, body);
        return "Email sent successfully to " + to;
    }

    @Operation(summary = "Confirm user account", description = "Confirms a user's account using a provided token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account confirmed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token")
    })
    @GetMapping("/confirm")
    public String confirmAccount(@RequestParam("token") String token) {
        return "Account confirmed successfully!";
    }

    @Operation(summary = "Get users by food log date", description = "Retrieves all users who have logged food on a specific date.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date format")
    })
    @GetMapping("/logged-on/{logDate}")
    public ResponseEntity<List<UserEntity>> getUsersByFoodLogDate(
            @Parameter(description = "Date to filter food logs (format: YYYY-MM-DD)") @PathVariable String logDate) {
        LocalDate date = LocalDate.parse(logDate);
        return ResponseEntity.ok(userService.findUsersByFoodLogDate(date));
    }

    @Operation(summary = "Get users by role and goal date range", description = "Retrieves users with a specific role who have goals within a date range.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date range or role")
    })
    @GetMapping("/role/{role}/goals")
    public ResponseEntity<List<UserEntity>> getUsersWithRoleAndGoalDateRange(
            @Parameter(description = "Role of the users to filter") @PathVariable String role,
            @Parameter(description = "Start date of the goal range (format: YYYY-MM-DD)") @RequestParam String startDate,
            @Parameter(description = "End date of the goal range (format: YYYY-MM-DD)") @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ResponseEntity.ok(userService.findUsersWithRoleAndGoalDateRange(role, start, end));
    }

    @Operation(summary = "Get users with food logs and vitals on a specific date", description = "Fetches users who have both food logs and vitals recorded on a specific date.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date format")
    })
    @GetMapping("/logs-and-vitals/{date}")
    public ResponseEntity<List<UserEntity>> getUsersWithLogsAndVitals(
            @Parameter(description = "Date to filter logs and vitals (format: YYYY-MM-DD)") @PathVariable String date) {
        LocalDate logDate = LocalDate.parse(date);
        return ResponseEntity.ok(userRepository.findUsersWithFoodLogsAndVitalsOnDate(logDate));
    }
}
