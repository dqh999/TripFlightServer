package com.airline.booking.domain.account.service.impl;


import com.airline.booking.domain.account.component.UserValidator;
import com.airline.booking.domain.account.exception.AccountExceptionCode;
import com.airline.booking.domain.account.model.User;
import com.airline.booking.domain.account.repository.UserRepository;
import com.airline.booking.domain.account.service.IUserService;
import com.airline.booking.domain.account.valueObject.Role;
import com.airline.booking.domain.exception.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public User register(User user) {
        validateUserForRegistration(user);

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new BusinessException(AccountExceptionCode.ACCOUNT_ALREADY_EXISTS);
        }

        setUserDefaults(user);

        return userRepository.save(user);
    }

    private void validateUserForRegistration(User user) {
        userValidator.validateUser(user);
    }

    private void setUserDefaults(User user) {
        user.initializeNewId();
        user.setRole(Role.USER().getValue());
        user.setAccountActive(true);
        user.setAccountLocked(false);
    }

    @Override
    public User login(
            String userName,
            String password
    ) {
        validateUserForLogin(userName, password);

        User existUser = getByUserName(userName);

        checkCredentials(existUser, password);

        return existUser;
    }

    private void validateUserForLogin(
            String phoneNumber,
            String password
    ) {
        userValidator.validatePhoneNumber(phoneNumber);
        userValidator.validatePassword(password);
    }

    private void checkCredentials(
            User existUser,
            String password
    ) {
        if (!existUser.getHashedPassword().matches(password)) {
            throw new BusinessException(AccountExceptionCode.INVALID_CREDENTIALS);
        }

        if (existUser.isAccountLocked()) {
            throw new BusinessException(AccountExceptionCode.ACCOUNT_LOCKED);
        }
        if (!existUser.isAccountActive()) {
            throw new BusinessException(AccountExceptionCode.ACCOUNT_INACTIVE);
        }
    }

    @Override
    @Transactional
    public User changePassword(
            User existUser,
            String oldPassword, String newPassword
    ) {
        validateChangePasswordRequest(oldPassword, newPassword);

        if (oldPassword.equals(newPassword)) {
            throw new BusinessException(AccountExceptionCode.INVALID_CREDENTIALS);
        }

        checkCredentials(existUser, oldPassword);

        if (existUser.getHashedPassword().matches(newPassword)) {
            throw new BusinessException(AccountExceptionCode.EXPIRED_PASSWORD);
        }

        existUser.setPassword(newPassword);

        userRepository.save(existUser);

        return existUser;
    }

    private void validateChangePasswordRequest(
            String oldPassword, String newPassword
    ) {
        userValidator.validatePassword(oldPassword);
        userValidator.validatePassword(newPassword);
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new BusinessException(AccountExceptionCode.ACCOUNT_NOT_FOUND));
    }
}
