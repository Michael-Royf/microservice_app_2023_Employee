package com.michael.notification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @SequenceGenerator(
            name = "notification_sequence",
            sequenceName = "notification_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_sequence")
    private Long notificationId;
    private Long employeeId;
    private String employeeEmail;
    private String employeeName;
    private String sender;
    private String message;
    private LocalDateTime sentAt;
}
