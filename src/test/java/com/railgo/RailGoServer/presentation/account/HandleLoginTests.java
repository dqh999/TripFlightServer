package com.railgo.RailGoServer.presentation.account;

import com.railgo.application.account.dataTransferObject.AccountDTO;
import com.railgo.application.account.dataTransferObject.request.LoginRequest;
import com.railgo.application.account.service.UserUseCase;
import com.railgo.infrastructure.exception.ApiResponse;
import com.railgo.presentation.account.AccountController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HandleLoginTests {
    @Mock
    private UserUseCase userUseCase;

    @InjectMocks
    private AccountController accountController;
    private LoginRequest loginRequest;
    private AccountDTO accountDTO;
    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("1234567890", "Test123!");

        accountDTO = new AccountDTO(
                "123",
                "Test",
                "User",
                LocalDate.of(2000,1,1),
                "USER",
                "test@example.com",
                "1234567890",
                "accessToken",
                "refreshToken"
        );
    }
    @Test
    void handleLogin_success() {
        when(userUseCase.login(loginRequest)).thenReturn(accountDTO);
        ResponseEntity<ApiResponse<AccountDTO>> response = accountController.handleLogin(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDTO, response.getBody().getData());
    }
    @Test
    void handleLogin_userCaseThrowsException() {
        when(userUseCase.login(loginRequest)).thenThrow(new RuntimeException("Login Error"));
        try {
            accountController.handleLogin(loginRequest);
        } catch (Exception e) {
            assertEquals("Login Error", e.getMessage());
        }
    }
}
