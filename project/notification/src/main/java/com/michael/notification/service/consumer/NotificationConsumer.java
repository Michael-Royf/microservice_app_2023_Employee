package com.michael.notification.service.consumer;

import com.michael.clients.notification.NotificationRequest;
import com.michael.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "notification.queue")
    public void consume(NotificationRequest notificationRequest) {
        notificationService.sendNotification(notificationRequest);
    }

}
