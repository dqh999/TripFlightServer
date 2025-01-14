package com.airline.booking.application.account.dataTransferObject;

import com.airline.booking.application.account.dataTransferObject.response.UserResponse;

public class AccountDTO {
    private UserResponse user;
    private String role;
    private String accessToken;
    private String refreshToken;
    public AccountDTO() {}

    public AccountDTO(UserResponse user, String accessToken, String refreshToken) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
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
