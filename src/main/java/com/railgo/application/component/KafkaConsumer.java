package com.railgo.application.component;

import com.railgo.application.ticket.service.ITicketUseCase;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.infrastructure.dataTransferObject.request.EmailRequest;
import com.railgo.infrastructure.service.messaging.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private final EmailService emailService;
    private final ITicketUseCase ticketUseCase;
    public KafkaConsumer(EmailService emailService,
                         ITicketUseCase ticketUseCase) {
        this.emailService = emailService;
        this.ticketUseCase = ticketUseCase;
    }

    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void listenOtpMailRequest(EmailRequest request) {
        emailService.send(request);
    }

    @KafkaListener(topics = "ticket-cancel", groupId = "ticket")
    public void listenTicketCancel(String ticketId){
        ticketUseCase.cancelTicket(ticketId);
    }
}
