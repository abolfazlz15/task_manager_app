package com.example.task_manager_app.email.interfaces;

import com.example.task_manager_app.email.enums.NotificationType;
import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {
    public void sendEmail(String to, String subject, NotificationType type, Context context) throws MessagingException;
}