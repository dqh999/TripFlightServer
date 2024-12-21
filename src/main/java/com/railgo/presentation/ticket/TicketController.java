package com.railgo.presentation.ticket;

import com.railgo.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.railgo.infrastructure.exception.ApiResponse;
import com.railgo.infrastructure.security.UserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> handleBookingTicket(@AuthenticationPrincipal UserDetail userRequest,
                                                 @RequestBody TicketBookingRequest request){
        return ApiResponse.build().toEntity();
    };
}
