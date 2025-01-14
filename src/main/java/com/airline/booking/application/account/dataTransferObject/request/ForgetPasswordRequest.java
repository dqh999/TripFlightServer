package com.airline.booking.application.account.dataTransferObject.request;

public class ForgetPasswordRequest {
    private String oldPassword;
    private String newPassword;

    public ForgetPasswordRequest() {
    }

    public ForgetPasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
