package com.example.railgo.application.account.dataTransferObject;


import com.example.railgo.domain.account.model.Token;
import com.example.railgo.domain.account.model.User;

public class AccountDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String phoneNumber;
    private String accessToken;
    private String refreshToken;


    public static AccountDTO from(User user, Token token) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(user.getId());
        accountDTO.setFirstName(user.getFirstName());
        accountDTO.setLastName(user.getLastName());
        accountDTO.setRole(user.getRole());
        accountDTO.setEmail(user.getEmail());
        accountDTO.setPhoneNumber(user.getPhoneNumber());

        accountDTO.setAccessToken(token.getValue());
        accountDTO.setRefreshToken(token.getRefreshToken());
        return accountDTO;
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
