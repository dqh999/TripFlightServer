package com.railgo.presentation.ticket;

import com.railgo.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketConfirmationRequest;
import com.railgo.application.ticket.service.ITicketUseCase;
import com.railgo.application.utils.RequestUtil;
import com.railgo.infrastructure.exception.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{ticketId}/confirm")
    public ResponseEntity<?> handleConfirmTicket(
            @PathVariable String ticketId,
            @RequestBody TicketConfirmationRequest request,
            HttpServletRequest httpServletRequest
    ) {
        var ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        request.setIpAddress(ipAddress);

        var result = ticketUseCase.confirm(ticketId, request);
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
