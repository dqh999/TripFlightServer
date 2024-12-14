package com.example.railgo.domain.account.component;

import com.example.railgo.domain.account.exception.AccountExceptionCode;
import com.example.railgo.domain.account.model.User;
import com.example.railgo.domain.account.valueObject.Role;
import com.example.railgo.domain.utils.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserValidator {
    public void validateUser(User user) {
        if (user == null) {
            throw new BusinessException();
        }

        validateFieldName(user.getFirstName());
        validateFieldName(user.getLastName());

        validateDateOfBirth(user.getDateOfBirth());
    }
    private void validateFieldName(String fieldName) {
        if (fieldName == null || fieldName.trim().isEmpty()) {
            throw new BusinessException(AccountExceptionCode.INVALID_NAME);
        }
        if (!fieldName.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
            throw new BusinessException(AccountExceptionCode.INVALID_NAME);
        }
    }
    public void validateDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            throw new BusinessException(AccountExceptionCode.INVALID_DATE_OF_BIRTH);
        }
        int age = calculateAge(dateOfBirth);
        if (age < 16) {
            throw new BusinessException(AccountExceptionCode.INVALID_DATE_OF_BIRTH);
        }
    }

    private int calculateAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - dateOfBirth.getYear();
        if (today.isBefore(dateOfBirth.plusYears(age))) {
            age--;
        }
        return age;
    }
    public void validateDefaultRole(String role) {
        if (role != null && !role.equals(Role.USER().getValue())){
            throw new IllegalArgumentException("Invalid role type.");
        }
    }
    public void validateEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BusinessException(AccountExceptionCode.INVALID_EMAIL);
        }
    }

    public void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^(\\+?\\d{1,3})?\\(?\\d{1,4}\\)?\\d{6,10}$")) {
            throw new BusinessException(AccountExceptionCode.INVALID_PHONE_NUMBER);
        }
    }
    public void validatePassword(String password) {
        if (password.length() < 8 && !password.matches(".*[!@#$%^&*].*")){
            throw new BusinessException(AccountExceptionCode.INVALID_PASSWORD_FORMAT);
        }
    }

}
