package edu.miu.cs.cs544.mercel.jpa.monitoring.email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Method to send registration email with security token
    public void sendRegistrationConfirmation(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Account Confirmation");
        message.setText("Please confirm your account by clicking the link: "
                + "http://localhost:7070/api/confirm?token=" + token);
        message.setFrom("vmercel@gmail.com"); // Your email address
        mailSender.send(message);
    }
}
