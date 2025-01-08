package com.airline.presentation.account;

import com.airline.application.account.dataTransferObject.AccountDTO;
import com.airline.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.airline.application.account.dataTransferObject.request.LoginRequest;
import com.airline.application.account.dataTransferObject.request.RefreshTokenRequest;
import com.airline.application.account.dataTransferObject.request.RegisterRequest;
import com.airline.application.account.service.UserUseCase;
import com.airline.infrastructure.exception.ApiResponse;
import com.airline.infrastructure.security.UserDetail;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.prefix}/account")
@Tag(name = "Account Controller")
public class AccountController {
    private final UserUseCase userUseCase;

    @Autowired
    public AccountController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AccountDTO>> handleRegister(@RequestBody RegisterRequest request) {
        var result = userUseCase.register(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccountDTO>> handleLogin(@RequestBody LoginRequest request) {
        var result = userUseCase.login(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }

    @PatchMapping("/changePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> handleChangePassword(@AuthenticationPrincipal UserDetail userRequest,
                                                  @RequestBody ChangePasswordRequest request) {
        var result = userUseCase.changePassword(userRequest, request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> handleRefreshToken(@RequestBody RefreshTokenRequest request) {
        var result = userUseCase.refreshToken(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }
}