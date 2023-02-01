package com.michael.clients.notification;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationRequest {
    private Long employeeId;
    private String employeeName;
    private String employeeEmail;
}
