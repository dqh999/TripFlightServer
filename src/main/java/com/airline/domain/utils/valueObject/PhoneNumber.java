package com.airline.domain.utils.valueObject;


import com.airline.domain.account.component.UserValidator;

public class PhoneNumber {
    private static final UserValidator userValidator = new UserValidator();
    private final String value;

    public PhoneNumber(String number) {
        if (number == null) {
            this.value = null;
            return;
        }
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
