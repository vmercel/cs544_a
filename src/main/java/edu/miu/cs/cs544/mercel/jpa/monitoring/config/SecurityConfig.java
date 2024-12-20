package edu.miu.cs.cs544.mercel.jpa.monitoring.config;

import edu.miu.cs.cs544.mercel.jpa.monitoring.user.UserEntity;
import edu.miu.cs.cs544.mercel.jpa.monitoring.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository; // Inject the UserRepository

    // UserDetailsService that retrieves users from the database
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserEntity user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }
            // Create a UserDetails object based on UserEntity
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole()) // Convert role to String
                    .build();
        };
    }

    // Password Encoder configuration
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security Filter Chain with CSRF disabled
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/api/**","/swagger-ui/**", "/v3/api-docs/**").hasRole("ADMIN")  // Restrict /api/** to ADMIN role
                .requestMatchers("/user/**").hasRole("USER")  // Restrict /user/** to USER role
                .anyRequest().authenticated()  // All other paths require authentication
                .and()
                .httpBasic()  // Enable HTTP Basic Authentication
                .and()
                .csrf().disable();  // Disable CSRF protection

        return http.build(); // Return the security configuration
    }
}