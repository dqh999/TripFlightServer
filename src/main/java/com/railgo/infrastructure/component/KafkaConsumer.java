package com.railgo.infrastructure.component;

import com.railgo.domain.ticket.model.Ticket;
import com.railgo.infrastructure.dataTransferObject.request.EmailRequest;
import com.railgo.infrastructure.service.messaging.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private final EmailService emailService;
    public KafkaConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void listenOtpMailRequest(EmailRequest request) {
        emailService.send(request);
    }
}
