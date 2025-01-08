package com.airline.application.account.dataTransferObject.request;

public class RefreshTokenRequest {
    private String accessToken;
    public RefreshTokenRequest() {}
    public RefreshTokenRequest(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
