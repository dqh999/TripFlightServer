package com.airline.booking.presentation.account.operations;

import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.dataTransferObject.request.*;
import com.airline.booking.infrastructure.exception.ApiResponse;
import com.airline.booking.infrastructure.security.UserDetail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

public interface AccountOperations {
    @PostMapping("/register")
    ResponseEntity<ApiResponse<AccountDTO>> handleRegister(
            @RequestBody RegisterRequest request,
            HttpServletRequest httpServletRequest
    );

    @PostMapping("/login")
    ResponseEntity<ApiResponse<AccountDTO>> handleLogin(
            @RequestBody LoginRequest request,
            HttpServletRequest httpServletRequest
    );

    @PostMapping("/logout")
    ResponseEntity<ApiResponse<Void>> handleLogout(@RequestBody LogoutRequest request);

    @PatchMapping("/changePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponse<AccountDTO>> handleChangePassword(
            @AuthenticationPrincipal UserDetail userRequest,
            @RequestBody ChangePasswordRequest request
    );

    @PostMapping("/refresh-token")
    ResponseEntity<ApiResponse<AccountDTO>> handleRefreshToken(@RequestBody RefreshTokenRequest request);
}
