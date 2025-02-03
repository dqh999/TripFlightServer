package com.airline.booking.application.account.service.implement;

import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.dataTransferObject.request.*;
import com.airline.booking.application.account.exception.AccountApplicationExceptionCode;
import com.airline.booking.application.account.mapper.AccountMapper;
import com.airline.booking.application.account.service.IAccountUseCase;
import com.airline.booking.infrastructure.exception.ApplicationException;
import com.airline.booking.domain.account.component.UserValidator;
import com.airline.booking.domain.account.exception.AccountExceptionCode;
import com.airline.booking.domain.account.model.Token;
import com.airline.booking.domain.account.model.User;
import com.airline.booking.domain.account.service.ITokenService;
import com.airline.booking.domain.account.service.IUserService;
import com.airline.booking.domain.exception.BusinessException;
import com.airline.booking.infrastructure.service.cache.CacheService;
import com.airline.booking.infrastructure.security.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountUseCaseImpl implements IAccountUseCase {
    private static final Logger logger = LoggerFactory.getLogger(AccountUseCaseImpl.class);

    private final IUserService userService;
    private final ITokenService tokenService;
    private final AccountMapper accountMapper;
    private final CacheService cacheService;

    @Value("${spring.jwt.expiration}")
    private long accessTokenTTL;
    private final String userDetailKey = "user_detail_id:%s";

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

        boolean isLocked = cacheService.acquireLock(phoneNumber, 300L);
        if (!isLocked) {
            throw new ApplicationException(AccountApplicationExceptionCode.LOCK_ALREADY_ACQUIRED);
        }
        try {
            User userRegister = accountMapper.toDTO(request);
            User newUser = userService.register(userRegister);
            return createAndStoreToken(newUser);
        } catch (BusinessException e) {
            logger.error("Error occurred during registration: {}", e.getMessage(), e);
            throw new BusinessException(e.getExceptionCode());
        } finally {
            cacheService.releaseLock(phoneNumber);
        }
    }

    private AccountDTO createAndStoreToken(User user) {
        Token newToken = tokenService.createToken(user);
        storeUserInCache(user);
        return buildAccountDTO(user, newToken);
    }

    private void storeUserInCache(User user) {
        UserDetail existUser = new UserDetail(
                user.getId(),
                user.getRole(),
                user.getPhoneNumber(), user.getPassword()
        );
        cacheService.put(
                userDetailKey.formatted(user.getId()),
                existUser,
                accessTokenTTL
        );
    }

    private AccountDTO buildAccountDTO(
            User user,
            Token token
    ) {
        return new AccountDTO(
                user.getId(), user.getRole(),
                token.getValue(), token.getExpiresAt(),
                token.getRefreshToken(), token.getRefreshTokenExpiresAt()
        );
    }

    @Override
    public AccountDTO login(LoginRequest request) {
        try {
            User existUser = userService.login(request.getUserName(), request.getPassword());
            return createAndStoreToken(existUser);
        } catch (BusinessException e) {
            logger.error("Login failed for user: {}. Reason: {}", request.getUserName(), e.getMessage());
            throw e;
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        UserValidator validator = new UserValidator();
        validator.validatePhoneNumber(phoneNumber);
    }

    @Override
    public void logout(LogoutRequest request) {

    }

    @Override
    public AccountDTO changePassword(
            UserDetail userRequest,
            ChangePasswordRequest request
    ) {
        String phoneNumber = userRequest.getUsername();
        User existUser = userService.getByUserName(phoneNumber);
        User userResult = userService.changePassword(existUser, request.getOldPassword(), request.getNewPassword());

        tokenService.revokeAllTokens(userResult.getId());

        return createAndStoreToken(existUser);
    }

    @Override
    public AccountDTO refreshToken(RefreshTokenRequest request) {
        String userName = tokenService.validateToken(request.getAccessToken());
        User existUser = userService.getByUserName(userName);
        return createAndStoreToken(existUser);
    }

    @Override
    public AccountDTO loginWithOAuth2(OAuth2LoginRequest request) {
        String userName = request.getEmail() != null ? request.getEmail() : request.getPhoneNumber();
        User existUser = userService.getByUserName(userName);
        return createAndStoreToken(existUser);
    }

    @Override
    public UserDetail authenticate(String accessToken) {
        String userName = tokenService.validateToken(accessToken);
        if (cacheService.exists(userDetailKey.formatted(userName))) {
            return cacheService.get(accessToken.formatted(userName), UserDetail.class);
        }
        throw new BusinessException(AccountExceptionCode.UNAUTHORIZED_ACCESS);
    }

}
