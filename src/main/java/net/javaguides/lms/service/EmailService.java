package net.javaguides.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String role) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Welcome to Library Management System");
            message.setText("Welcome! Your account has been created as a " + role + ".\n\n" +
                    "You can now login to access the system.");
            
            mailSender.send(message);
        } catch (Exception e) {
            // Log error but don't fail the registration
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    public void sendReservationConfirmation(String to, String bookTitle, String dueDate) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Book Reservation Confirmation");
            message.setText("You have successfully reserved: " + bookTitle + "\n" +
                    "Due Date: " + dueDate + "\n\n" +
                    "Please return the book on time.");
            
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}