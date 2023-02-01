package com.michael.notification.service.impl;

import com.michael.clients.notification.NotificationRequest;
import com.michael.notification.entity.Notification;
import com.michael.notification.repository.NotificationRepository;
import com.michael.notification.service.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private JavaMailSender mailSender;

    public static final String FAILED_TO_SEND_EMAIL = "Failed to send email";
    public static final String FROM_EMAIL = "microservice_support@gmail.com";
    public static final String GREETING = "Hello  %s,\n\n Welcome to the app \n\nThe Support Team";
    public static final String SENDER = "The Support Team";


    @Override
    @Async
    public void sendNotification(NotificationRequest notificationRequest) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(String.format(GREETING, notificationRequest.getEmployeeName()));
            helper.setTo(notificationRequest.getEmployeeEmail());
            helper.setFrom(FROM_EMAIL);
            helper.setSubject("Greeting");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
            throw new RuntimeException(FAILED_TO_SEND_EMAIL);
        }
        Notification notification = Notification.builder()
                .employeeId(notificationRequest.getEmployeeId())
                .employeeEmail(notificationRequest.getEmployeeEmail())
                .employeeName(notificationRequest.getEmployeeName())
                .sender(SENDER)
                .message(String.format(GREETING, notificationRequest.getEmployeeName()))
                .sentAt(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
    }
}
