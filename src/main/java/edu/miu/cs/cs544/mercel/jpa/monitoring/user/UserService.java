package edu.miu.cs.cs544.mercel.jpa.monitoring.user;
import edu.miu.cs.cs544.mercel.jpa.monitoring.email.EmailService;
import edu.miu.cs.cs544.mercel.jpa.monitoring.email.TokenGenerator;
import edu.miu.cs.cs544.mercel.jpa.monitoring.goals.Goal;
import edu.miu.cs.cs544.mercel.jpa.monitoring.jms.JmsProducer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;
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

    public List<UserEntity> findUsersByFoodLogDate(LocalDate date) {
        return userRepository.findUsersByFoodLogDate(date);
    }


    public List<UserEntity> findUsersWithRoleAndGoalDateRange(String role, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);

        Root<UserEntity> user = query.from(UserEntity.class);
        Join<UserEntity, Goal> goal = user.join("goals");

        query.select(user)
                .where(cb.and(
                        cb.equal(user.get("role"), role),
                        cb.between(goal.get("startDate"), startDate, endDate)
                ));

        TypedQuery<UserEntity> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}

