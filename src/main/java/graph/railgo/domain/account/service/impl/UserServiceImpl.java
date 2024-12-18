package graph.railgo.domain.account.service.impl;


import graph.railgo.domain.account.component.UserValidator;
import graph.railgo.domain.account.exception.AccountExceptionCode;
import graph.railgo.domain.account.model.User;
import graph.railgo.domain.account.repository.UserRepository;
import graph.railgo.domain.account.service.IUserService;
import graph.railgo.domain.account.valueObject.Role;
import graph.railgo.domain.utils.exception.BusinessException;
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
        userValidator.validateDefaultRole(user.getRole());
    }

    private void setUserDefaults(User user) {
        user.initializeNewId();
        user.setRole(Role.USER().getValue());
        user.setAccountActive(true);
        user.setAccountLocked(false);
    }

    @Override
    public User login(String phoneNumber, String password) {
        validateUserForLogin(phoneNumber, password);

        User existUser = getUserByPhoneNumber(phoneNumber);

        checkCredentials(existUser, password);

        return existUser;
    }

    private void validateUserForLogin(String phoneNumber, String password) {
        userValidator.validatePhoneNumber(phoneNumber);
        userValidator.validatePassword(password);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new BusinessException(AccountExceptionCode.ACCOUNT_NOT_FOUND));
    }

    private void checkCredentials(User existUser, String password) {
        if (!existUser.getHashedPassword().matches(password)) {
            throw new BusinessException(AccountExceptionCode.INVALID_CREDENTIALS);
        }

//        if (existUser.isAccountLocked()) {
//            throw new BusinessException(AccountExceptionCode.ACCOUNT_LOCKED);
//        }
//        if (!existUser.isAccountActive()) {
//            throw new BusinessException(AccountExceptionCode.ACCOUNT_INACTIVE);
//        }
    }

    @Override
    public User changePassword(String phoneNumber,
                               String oldPassword, String newPassword) {
        validateChangePasswordRequest(phoneNumber,oldPassword, newPassword);

        if (oldPassword.equals(newPassword)) {
            throw new BusinessException(AccountExceptionCode.INVALID_CREDENTIALS);
        }

        User existUser = this.getUserByPhoneNumber(phoneNumber);

        checkCredentials(existUser, oldPassword);

        if (existUser.getHashedPassword().matches(newPassword)) {
            throw new BusinessException(AccountExceptionCode.EXPIRED_PASSWORD);
        }

        existUser.setPassword(newPassword);

        userRepository.save(existUser);

        return existUser;
    }

    private void validateChangePasswordRequest(String phoneNumber,String oldPassword, String newPassword) {
        userValidator.validatePhoneNumber(phoneNumber);
        userValidator.validatePassword(oldPassword);
        userValidator.validatePassword(newPassword);
    }
}
