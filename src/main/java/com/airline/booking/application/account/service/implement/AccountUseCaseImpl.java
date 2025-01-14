package com.airline.booking.application.account.service.implement;

import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.airline.booking.application.account.dataTransferObject.request.LoginRequest;
import com.airline.booking.application.account.dataTransferObject.request.RefreshTokenRequest;
import com.airline.booking.application.account.dataTransferObject.request.RegisterRequest;
import com.airline.booking.application.account.dataTransferObject.response.UserResponse;
import com.airline.booking.application.account.exception.AccountApplicationExceptionCode;
import com.airline.booking.application.account.mapper.AccountMapper;
import com.airline.booking.application.account.service.AccountUseCase;
import com.airline.booking.application.utils.exception.ApplicationException;
import com.airline.booking.domain.account.component.UserValidator;
import com.airline.booking.domain.account.exception.AccountExceptionCode;
import com.airline.booking.domain.account.model.Token;
import com.airline.booking.domain.account.model.User;
import com.airline.booking.domain.account.service.ITokenService;
import com.airline.booking.domain.account.service.IUserService;
import com.airline.booking.domain.utils.exception.BusinessException;
import com.airline.booking.infrastructure.service.cache.CacheService;
import com.airline.booking.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountUseCaseImpl implements AccountUseCase {
    private final IUserService userService;
    private final ITokenService tokenService;
    private final AccountMapper accountMapper;
    private final CacheService cacheService;

    @Value("${spring.jwt.expiration}")
    private long accessTokenTTL;

    @Autowired
    public AccountUseCaseImpl(IUserService userService,
                              ITokenService tokenService,
                              AccountMapper accountMapper,
                              CacheService cacheService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.accountMapper = accountMapper;
        this.cacheService = cacheService;
    }


    @Override
    public AccountDTO register(RegisterRequest request) {

        String phoneNumber = request.getPhoneNumber();
        validatePhoneNumber(phoneNumber);
        cacheService.delete(phoneNumber);

        boolean isLocked = cacheService.acquireLock(phoneNumber, 300);
        if (!isLocked) {
            throw new ApplicationException(AccountApplicationExceptionCode.LOCK_ALREADY_ACQUIRED);
        }
        try {
            User userRegister = accountMapper.toDTO(request);
            User newUser = userService.register(userRegister);

            Token token = tokenService.createToken(newUser);
            String accessToken = token.getValue();

            AccountDTO accountDTO = buildAccountDTO(newUser, token);

            storeAccessTokenInCache(accessToken, newUser);

            return accountDTO;
        } catch (BusinessException e) {
            throw new BusinessException(e.getExceptionCode());
        } finally {
            cacheService.releaseLock(phoneNumber);
        }
    }

    private void storeAccessTokenInCache(
            String accessToken,
            User user
    ) {
        UserDetail existUser = new UserDetail(
                user.getId(),
                user.getRole(),
                user.getPhoneNumber(),
                user.getPassword()
        );
        cacheService.put(accessToken, existUser, accessTokenTTL);
    }
    private AccountDTO buildAccountDTO(
            User user,
            Token token
    ){
        UserResponse userResponse = accountMapper.toResponse(user);
        return new AccountDTO(
                userResponse,
                token.getValue(),
                token.getRefreshToken()
        );
    }
    @Override
    public AccountDTO login(LoginRequest request) {
        User existUser = userService.login(request.getPhoneNumber(), request.getPassword());

        Token token = tokenService.createToken(existUser);
        String accessToken = token.getValue();

        AccountDTO accountDTO = buildAccountDTO(existUser, token);

        storeAccessTokenInCache(accessToken, existUser);

        return accountDTO;
    }

    private void validatePhoneNumber(String phoneNumber) {
        UserValidator validator = new UserValidator();
        validator.validatePhoneNumber(phoneNumber);
    }

    @Override
    public AccountDTO changePassword(
            UserDetail userRequest,
            ChangePasswordRequest request
    ) {
        AuthenticationPrincipal principal = (AuthenticationPrincipal) SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = userRequest.getUsername();

        User userResult = userService.changePassword(phoneNumber, request.getOldPassword(), request.getNewPassword());

        tokenService.revokeAllTokens(userResult.getId());

        Token token = tokenService.createToken(userResult);
        String accessToken = token.getValue();

        AccountDTO accountDTO = buildAccountDTO(userResult, token);

        storeAccessTokenInCache(accessToken, userResult);

        return accountDTO;
    }

    @Override
    public AccountDTO refreshToken(RefreshTokenRequest request) {
        String userName = tokenService.validateToken(request.getAccessToken());
        User existUser = userService.getUserByPhoneNumber(userName);
        Token newToken = tokenService.createToken(existUser);

        AccountDTO accountDTO = buildAccountDTO(existUser, newToken);

        storeAccessTokenInCache(newToken.getValue(), existUser);
        return accountDTO;
    }

    @Override
    public UserDetail authenticate(String accessToken) {
        tokenService.validateToken(accessToken);
        if (cacheService.exists(accessToken)) {
            return cacheService.get(accessToken, UserDetail.class);
        }
        throw new BusinessException(AccountExceptionCode.UNAUTHORIZED_ACCESS);
    }
}
