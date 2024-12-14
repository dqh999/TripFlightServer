package com.example.railgo.domain.account.valueObject;


import com.example.railgo.domain.account.component.UserValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashPassword {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final UserValidator userValidator = new UserValidator();
    private final String value;

    public HashPassword(String rawPassword) {
        userValidator.validatePassword(rawPassword);
        this.value = hashPassword(rawPassword);
    }

    private String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.value);
    }

    public HashPassword updatePassword(String rawPassword) {
        return new HashPassword(rawPassword);
    }
    public String getHashedPassword() {
        return this.value;
    }
    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}
