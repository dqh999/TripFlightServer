package com.flight.server.application.utils.component;

import com.flight.server.application.ticket.service.ITicketUseCase;
import com.flight.server.infrastructure.dataTransferObject.request.EmailRequest;
import com.flight.server.infrastructure.service.messaging.EmailService;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
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

    @KafkaListener(
            topics = "${spring.kafka.topic.email}",
            groupId = "email-group"
    )
    public void listenOtpMailRequest(EmailRequest request) {
        logger.info("Received OTP email request for email: {}", request.getTo());
        emailService.send(request);
    }

    @RetryableTopic(    // Non-blocking
            attempts = "4", // 1 primary topic + 3 retry topics + 1 DLT topic
            backoff = @Backoff(delay = 1000, multiplier = 2), // exponential backoff is better than linear backoff here - 1, 2, 4
            dltStrategy = DltStrategy.FAIL_ON_ERROR, // No retry on DLT topic
            autoCreateTopics = "true",  // true: for test; false: recommended
            include = {RetriableException.class, RuntimeException.class}    // RuntimeException: for test; mark retriable business exceptions
    )
    @KafkaListener(
            topics = "${spring.kafka.topic.ticket.ticket-cancel}",
            groupId = "ticket"
    )
    public void listenTicketCancel(String ticketId) {
        logger.info("Received ticket cancel request for ticketId: {}", ticketId);
        ticketUseCase.cancelTicket(ticketId);
    }
    @DltHandler
    void retryTicketCancelCommand(@Payload String message) {
        logger.error("Alert Booking Command: {}", message);
    }
}
