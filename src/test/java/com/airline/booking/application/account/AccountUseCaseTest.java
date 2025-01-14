package com.airline.booking.application.account;

import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.dataTransferObject.request.RegisterRequest;
import com.airline.booking.application.account.dataTransferObject.response.UserResponse;
import com.airline.booking.application.account.mapper.AccountMapper;
import com.airline.booking.application.account.service.AccountUseCase;
import com.airline.booking.domain.account.model.Token;
import com.airline.booking.domain.account.model.User;
import com.airline.booking.domain.account.service.ITokenService;
import com.airline.booking.domain.account.service.IUserService;
import com.airline.booking.infrastructure.service.cache.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountUseCaseTest {
    @Mock
    private IUserService userService;
    @Mock
    private ITokenService tokenService;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private CacheService cacheService;

    @InjectMocks
    AccountUseCase accountUseCase;

    private RegisterRequest registerRequest;
    private AccountDTO accountDTO;
    private UserResponse userResponse;
    private User user;
    private Token token;

    @BeforeEach
    void init() {
        registerRequest = new RegisterRequest(
                "192.168.1.1",
                "0969999999",
                "user@example.com",
                "Password123@",
                "John",
                "Doe",
                LocalDate.of(1995, 5, 15),
                "MALE"
        );
        userResponse = new UserResponse(
                "user123",
                "John",
                "Doe",
                LocalDate.of(1995, 5, 15),
                "USER",
                "user@example.com",
                "0969999999"
        );
        accountDTO = new AccountDTO(
                userResponse,
                "access-token",
                "refresh-token"
        );

        user = new User(
                "user123",
                "John",
                "Doe",
                LocalDate.of(1995, 5, 15),
                "MALE",
                "USER",
                "user@example.com",
                "0969999999",
                "Password123@",
                false,
                true
        );

        token = new Token(
                "user123",
                "access-token",
                new Date(),
                new Date(System.currentTimeMillis() + 3600000),
                false,
                "refresh-token",
                new Date(System.currentTimeMillis() + 86400000)
        );
    }

    @Test
    @Disabled
    void testRegister_withValidRequest_shouldReturnAccountDTO() {
        when(cacheService.acquireLock(eq(registerRequest.getPhoneNumber()), eq(300L))).thenReturn(true);
        when(accountMapper.toDTO(any(RegisterRequest.class))).thenReturn(user);
        when(userService.register(any(User.class))).thenReturn(user);
        when(tokenService.createToken(any(User.class))).thenReturn(token);
        when(accountMapper.toDTO(any(RegisterRequest.class))).thenReturn(user);

        AccountDTO result = accountUseCase.register(registerRequest);
        assertNotNull(result);
//        assertEquals(accountDTO.getId(), result.getId());
//        assertEquals(accountDTO.getFirstName(), result.getFirstName());
//        assertEquals(accountDTO.getLastName(), result.getLastName());
//        assertEquals(accountDTO.getEmail(), result.getEmail());
//        assertEquals(accountDTO.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(accountDTO.getAccessToken(), result.getAccessToken());
        assertEquals(accountDTO.getRefreshToken(), result.getRefreshToken());

        verify(cacheService).acquireLock(eq(registerRequest.getPhoneNumber()), eq(300L));
        verify(userService).register(any(User.class));
        verify(tokenService).createToken(any(User.class));
        verify(cacheService).releaseLock(registerRequest.getPhoneNumber());

    }
}
