package com.example.railgo.domain.account.valueObject;


import com.example.railgo.domain.account.component.UserValidator;

public class PhoneNumber {
    private static final UserValidator userValidator = new UserValidator();
    private final String value;

    public PhoneNumber(String number) {
        userValidator.validatePhoneNumber(number);
        this.value = number;
    }

    public PhoneNumber updatePhoneNumber(String newNumber) {
        userValidator.validatePhoneNumber(newNumber);
        return new PhoneNumber(newNumber);
    }

    public String getValue() {
        return this.value;
    }
}
