package com.airline.application.component;

import com.airline.application.ticket.service.ITicketUseCase;
import com.airline.infrastructure.dataTransferObject.request.EmailRequest;
import com.airline.infrastructure.service.messaging.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final EmailService emailService;
    private final ITicketUseCase ticketUseCase;

    public KafkaConsumer(EmailService emailService,
                         ITicketUseCase ticketUseCase) {
        this.emailService = emailService;
        this.ticketUseCase = ticketUseCase;
    }

    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void listenOtpMailRequest(EmailRequest request) {
        logger.info("Received OTP email request for email: {}", request.getTo());
        emailService.send(request);
    }

    @KafkaListener(topics = "ticket-cancel", groupId = "ticket")
    public void listenTicketCancel(String ticketId) {
        logger.info("Received ticket cancel request for ticketId: {}", ticketId);
        ticketUseCase.cancelTicket(ticketId);
    }
}
