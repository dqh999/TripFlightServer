package com.example.railgo.presentation.account;

import com.example.railgo.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.example.railgo.application.account.dataTransferObject.request.LoginRequest;
import com.example.railgo.application.account.dataTransferObject.request.RegisterRequest;
import com.example.railgo.infrastructure.security.UserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/account")
public interface AccountOperation {
    @PostMapping("/register")
    ResponseEntity<?> handleRegister(@RequestBody RegisterRequest request);

    @PostMapping("/login")
    ResponseEntity<?> handleLogin(@RequestBody LoginRequest request);

    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<?> handleChangePassword(@AuthenticationPrincipal UserDetail userRequest,
                                           @RequestBody ChangePasswordRequest request);
}
