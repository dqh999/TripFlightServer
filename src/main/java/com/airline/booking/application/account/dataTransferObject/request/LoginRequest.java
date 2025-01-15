package com.airline.booking.application.account.dataTransferObject.request;

public class LoginRequest {
    private String ipAddress;
    private String userName;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String ipAddress, String userName, String password) {
        this.ipAddress = ipAddress;
        this.userName = userName;
        this.password = password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
