package com.airline.booking.presentation.account;

import com.airline.booking.application.account.dataTransferObject.response.UserResponse;
import com.airline.booking.application.account.service.UserUseCase;
import com.airline.booking.infrastructure.exception.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/user")
public class UserController {
    private final UserUseCase userUseCase;
    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<UserResponse>> handleGetCurrentUser() {
        var result = userUseCase.getCurrentUser();
        return ApiResponse.<UserResponse>build()
                .withData(result)
                .toEntity();
    }
}
