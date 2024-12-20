package edu.miu.cs.cs544.mercel.jpa.monitoring.email;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Set mail server properties (use values from your application.properties or hardcode here)
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("vmercel@gmail.com");
//        mailSender.setPassword("otproexjyciudvso");
        mailSender.setHost("smtp.mailersend.net");
        mailSender.setPort(587);
        mailSender.setUsername("MS_YXtfR9@trial-o65qngkwvy8gwr12.mlsender.net");
        mailSender.setPassword("wGKE3KfVdYtJ4B3M");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}

