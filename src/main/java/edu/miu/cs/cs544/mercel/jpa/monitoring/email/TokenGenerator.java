package edu.miu.cs.cs544.mercel.jpa.monitoring.email;


import java.util.UUID;

public class TokenGenerator {

    public static String generateToken() {
        return UUID.randomUUID().toString();  // Generate a random token
    }
}

