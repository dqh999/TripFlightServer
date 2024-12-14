package com.example.railgo.presentation.account;

import com.example.railgo.application.account.dataTransferObject.request.LoginRequest;
import com.example.railgo.application.account.dataTransferObject.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/account")
public interface AccountOperation {
    @PostMapping("/register")
    ResponseEntity<?> handleRegister(@RequestBody RegisterRequest request);

    @PostMapping("/login")
    ResponseEntity<?> handleLogin(@RequestBody LoginRequest request);
}
