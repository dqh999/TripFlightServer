package com.flight.server.application.account.dataTransferObject;


import com.flight.server.domain.account.model.Token;
import com.flight.server.domain.account.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class AccountDTO {
    private String id;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String role;
    private String email;
    private String phoneNumber;
    private String accessToken;
    private String refreshToken;
    public AccountDTO() {}

    public AccountDTO(String id, String firstName, String lastName,LocalDate dateOfBirth, String role, String email, String phoneNumber, String accessToken, String refreshToken) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public static AccountDTO from(User user) {
        return new AccountDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getRole(),
                user.getEmail(),
                user.getPhoneNumber(),
                null,
                null
        );
    }

    public static AccountDTO from(User user, Token token) {
        return new AccountDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getRole(),
                user.getEmail(),
                user.getPhoneNumber(),
                token.getValue(),
                token.getRefreshToken()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
