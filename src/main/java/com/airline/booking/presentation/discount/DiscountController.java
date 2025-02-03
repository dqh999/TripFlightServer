package com.airline.booking.presentation.discount;

import com.airline.booking.infrastructure.exception.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/discount")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DiscountController {

    @PostMapping
    public ResponseEntity<ApiResponse<?>> handleAddDiscount(){
        return null;
    };

    @GetMapping
    public ResponseEntity<ApiResponse<?>> handleGetAllDiscount() {
        return null;
    }
}
