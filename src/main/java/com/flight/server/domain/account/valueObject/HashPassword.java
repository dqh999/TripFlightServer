package com.flight.server.domain.account.valueObject;


import com.flight.server.domain.account.component.UserValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashPassword {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final UserValidator userValidator = new UserValidator();
    private String value;

    public HashPassword(String password) {
        if (password == null){
            return;
        }
        if (isHashedPassword(password)) {
            this.value = password;
            return;
        }
        userValidator.validatePassword(password);
        this.value = hashPassword(password);
    }

    private boolean isHashedPassword(String password) {
        if (password == null || password.length() != 60) {
            return false;
        }

        return password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$");
    }


    private String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.value);
    }

    public void setValue(String encodedPassword) {
        this.value = encodedPassword;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}
