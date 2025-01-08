package com.airline.presentation.ticket;

import com.airline.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.airline.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.airline.application.ticket.dataTransferObject.request.TicketBookRequest;
import com.airline.application.ticket.service.ITicketUseCase;
import com.airline.application.utils.RequestUtil;
import com.airline.infrastructure.exception.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.prefix}/ticket")
@Tag(name = "Ticket Controller")
public class TicketController {
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    private final ITicketUseCase ticketUseCase;

    @Autowired
    public TicketController(ITicketUseCase ticketUseCase) {
        this.ticketUseCase = ticketUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<?> handleCreateTicket(@RequestBody TicketBookingRequest request) {
        logger.info("Received request to create ticket: {}", request);

        var result = ticketUseCase.create(request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/{ticketId}/book")
    public ResponseEntity<?> handleBookingTicket(
            @PathVariable String ticketId,
            @RequestBody TicketBookRequest request,
            HttpServletRequest httpServletRequest
    ) {
        var ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        request.setIpAddress(ipAddress);

        logger.info("Received request to book ticket. Ticket ID: {}, Request: {}, IP: {}",
                ticketId, request, ipAddress);

        var result = ticketUseCase.book(ticketId, request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/{ticketId}/apply-discount")
    public ResponseEntity<?> handleApplyDiscount(
            @PathVariable String ticketId,
            @RequestBody ApplyDiscountRequest request) {
        var result = ticketUseCase.applyDiscount(ticketId, request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
}
