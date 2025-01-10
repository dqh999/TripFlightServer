package com.flight.server.domain.utils.valueObject;


import com.flight.server.domain.account.component.UserValidator;

public class Email {
    private static final UserValidator userValidator = new UserValidator();
    private final String value;

    public Email(String email) {
        if (email == null) {
            this.value = null;
            return;
        }
        userValidator.validateEmail(email);
        this.value = email;
    }

    public String getValue() {
        return this.value;
    }
}
