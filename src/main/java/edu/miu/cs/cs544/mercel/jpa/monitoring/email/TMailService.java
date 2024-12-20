package edu.miu.cs.cs544.mercel.jpa.monitoring.email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class TMailService {

    @Autowired
    private JavaMailSender mailSender;

    // Method to send a simple email
    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);  // Recipient email
        message.setSubject(subject);  // Email subject
        message.setText(body);  // Email body
        message.setFrom("no-reply@testmail.app");  // Sender's email (use a valid inbox name from Testmail.app)

        mailSender.send(message);  // Send the email
    }
}

