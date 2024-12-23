package com.railgo.presentation.ticket;

import com.railgo.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.railgo.application.ticket.service.ITicketUseCase;
import com.railgo.infrastructure.exception.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final ITicketUseCase ticketUseCase;

    @Autowired
    public TicketController(ITicketUseCase ticketUseCase) {
        this.ticketUseCase = ticketUseCase;
    }

    @PostMapping
    public ResponseEntity<?> handleBookingTicket(@RequestBody TicketBookingRequest request) {
        var result = ticketUseCase.book(request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
}
