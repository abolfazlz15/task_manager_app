package com.example.task_manager_app.email.service;

import com.example.task_manager_app.email.enums.NotificationType;
import com.example.task_manager_app.email.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(String to, String subject, NotificationType type, Context context) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);

        String htmlContent = templateEngine.process(type.value, context);
        helper.setText(htmlContent, true);

        emailSender.send(message);
    }
}
