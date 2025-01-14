package com.airline.booking.application.account.dataTransferObject.request;

public class RefreshTokenRequest {
    private String accessToken;
    private String refreshToken;

    public RefreshTokenRequest() {}
    public RefreshTokenRequest(String accessToken,String refreshToken)
    {
        this.accessToken=accessToken;
        this.refreshToken = refreshToken;
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
