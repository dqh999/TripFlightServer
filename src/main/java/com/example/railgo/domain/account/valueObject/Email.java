package com.example.railgo.domain.account.valueObject;


import com.example.railgo.domain.account.component.UserValidator;

public class Email {
    private static final UserValidator userValidator = new UserValidator();
    private final String value;

    public Email(String email) {
        userValidator.validatePhoneNumber(email);
        this.value = email;
    }

    public String getValue() {
        return this.value;
    }
}
