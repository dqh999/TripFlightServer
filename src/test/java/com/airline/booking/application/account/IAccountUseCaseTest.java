package com.airline.booking.application.account;

import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.dataTransferObject.request.RegisterRequest;
import com.airline.booking.application.account.mapper.AccountMapper;
import com.airline.booking.application.account.service.implement.AccountUseCaseImpl;
import com.airline.booking.infrastructure.exception.ApplicationException;
import com.airline.booking.domain.account.model.Token;
import com.airline.booking.domain.account.model.User;
import com.airline.booking.domain.account.service.ITokenService;
import com.airline.booking.domain.account.service.IUserService;
import com.airline.booking.infrastructure.service.cache.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IAccountUseCaseTest {
    @Mock
    private IUserService userService;
    @Mock
    private ITokenService tokenService;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private CacheService cacheService;

    @InjectMocks
    AccountUseCaseImpl accountUseCase;

    private RegisterRequest registerRequest;
    private AccountDTO accountDTO;
    private User user;
    private Token token;

    @BeforeEach
    void init() {
        Date accessTokenExpiresAt = new Date(System.currentTimeMillis() + 3600000);
        Date refreshTokenExpiresAt = new Date(System.currentTimeMillis() + 86400000);

        registerRequest = new RegisterRequest(
                "192.168.1.1",
                "0969999999",
                "Password123@",
                "John",
                "Doe",
                LocalDate.of(1995, 5, 15),
                "MALE"
        );

        accountDTO = new AccountDTO(
                "user123",
                "USER",
                "access-token",
                accessTokenExpiresAt,
                "refresh-token",
                refreshTokenExpiresAt
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
                accessTokenExpiresAt,
                false,
                "refresh-token",
                refreshTokenExpiresAt
        );
    }

    @Test
//    @Disabled
    void testRegister_withValidRequest_shouldReturnAccountDTO() {
        when(cacheService.acquireLock(eq(registerRequest.getPhoneNumber()), eq(300L))).thenReturn(true);
        when(accountMapper.toDTO(any(RegisterRequest.class))).thenReturn(user);
        when(userService.register(any(User.class))).thenReturn(user);
        when(tokenService.createToken(any(User.class))).thenReturn(token);

        AccountDTO result = accountUseCase.register(registerRequest);
        assertNotNull(result);
        assertEquals(accountDTO.getAccessToken(), result.getAccessToken());
        assertEquals(accountDTO.getRefreshToken(), result.getRefreshToken());

        verify(cacheService).acquireLock(eq(registerRequest.getPhoneNumber()), eq(300L));
        verify(userService).register(any(User.class));
        verify(tokenService).createToken(any(User.class));
        verify(cacheService).releaseLock(registerRequest.getPhoneNumber());

    }
    @Test
    void testRegister_withLockAcquired_shouldThrowApplicationException() {
        when(cacheService.acquireLock(any(), anyLong())).thenReturn(false);

        assertThrows(ApplicationException.class, () -> {
            accountUseCase.register(registerRequest);
        });

        verify(cacheService).acquireLock(eq(registerRequest.getPhoneNumber()), eq(300L));
    }
}
