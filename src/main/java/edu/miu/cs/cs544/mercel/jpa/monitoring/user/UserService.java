package edu.miu.cs.cs544.mercel.jpa.monitoring.user;
import edu.miu.cs.cs544.mercel.jpa.monitoring.email.EmailService;
import edu.miu.cs.cs544.mercel.jpa.monitoring.email.TokenGenerator;
import edu.miu.cs.cs544.mercel.jpa.monitoring.jms.JmsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JmsProducer producer;

    @Value("${jms.queue.food-log-send}")
    private String jmsQueueName;

    // Method to create a new user and send a registration confirmation email
    public UserEntity registerUser(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }

        // Hash the user's password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Generate a security token
        String token = TokenGenerator.generateToken();

        // Save the user
        userRepository.save(user);

        // Create a formatted, multi-line message
        String message = String.format(
                "New user registration details:\n" +
                        "---------------------------------\n" +
                        "Name: %s\n" +
                        "Email: %s\n" +
                        "Username: %s\n" +
                        "Role: %s\n" +
                        "Token: %s\n" +
                        "---------------------------------\n",
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                token
        );

// Replace with the actual queue name or dynamically resolve it
        producer.sendMessage(jmsQueueName, message);

        return user;
    }



    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserEntity not found with id: " + id));
    }

    public UserEntity updateUser(Long id, UserEntity updatedUser) {
        UserEntity existingUser = getUserById(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

