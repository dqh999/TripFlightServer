package com.airline.booking.presentation.ticket;

import com.airline.booking.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.airline.booking.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.airline.booking.application.ticket.dataTransferObject.request.TicketConfirmRequest;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketBookingResponse;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketConfirmResponse;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketResponse;
import com.airline.booking.application.ticket.service.ITicketUseCase;
import com.airline.booking.application.utils.RequestUtil;
import com.airline.booking.infrastructure.exception.ApiResponse;
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

    @PostMapping("/booking")
    public ResponseEntity<ApiResponse<TicketBookingResponse>> handleCreateTicket(@RequestBody TicketBookingRequest request) {
        logger.info("Received request to create ticket: {}", request);

        var result = ticketUseCase.booking(request);
        return ApiResponse.<TicketBookingResponse>build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/{ticketId}/confirm")
    public ResponseEntity<ApiResponse<TicketConfirmResponse>> handleBookingTicket(
            @PathVariable String ticketId,
            @RequestBody TicketConfirmRequest request,
            HttpServletRequest httpServletRequest
    ) {
        var ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        request.setIpAddress(ipAddress);

        logger.info("Received request to book ticket. Ticket ID: {}, Request: {}, IP: {}",
                ticketId, request, ipAddress);

        var result = ticketUseCase.confirm(ticketId, request);
        return ApiResponse.<TicketConfirmResponse>build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/{ticketId}/apply-discount")
    public ResponseEntity<ApiResponse<TicketResponse>> handleApplyDiscount(
            @PathVariable String ticketId,
            @RequestBody ApplyDiscountRequest request
    ) {
        var result = ticketUseCase.applyDiscount(ticketId, request);
        return ApiResponse.<TicketResponse>build()
                .withData(result)
                .toEntity();
    }
}
