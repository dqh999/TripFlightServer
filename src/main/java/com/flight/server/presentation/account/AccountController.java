package com.flight.server.presentation.account;

import com.flight.server.application.account.dataTransferObject.AccountDTO;
import com.flight.server.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.flight.server.application.account.dataTransferObject.request.LoginRequest;
import com.flight.server.application.account.dataTransferObject.request.RefreshTokenRequest;
import com.flight.server.application.account.dataTransferObject.request.RegisterRequest;
import com.flight.server.application.account.service.UserUseCase;
import com.flight.server.infrastructure.exception.ApiResponse;
import com.flight.server.infrastructure.security.UserDetail;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.prefix}/account")
@Tag(name = "Account Controller")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final UserUseCase userUseCase;

    @Autowired
    public AccountController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AccountDTO>> handleRegister(
            @RequestBody RegisterRequest request
    ) {
        var result = userUseCase.register(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccountDTO>> handleLogin(
            @RequestBody LoginRequest request
    ) {
        var result = userUseCase.login(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }

    @PatchMapping("/changePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<AccountDTO>> handleChangePassword(
            @AuthenticationPrincipal UserDetail userRequest,
            @RequestBody ChangePasswordRequest request
    ) {
        var result = userUseCase.changePassword(userRequest, request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<ApiResponse<AccountDTO>> handleRefreshToken(
            @RequestBody RefreshTokenRequest request
    ) {
        var result = userUseCase.refreshToken(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }
}