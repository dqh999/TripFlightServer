package com.airline.application.payment.dataTransferObject.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IpnResponse {
    @JsonProperty("RspCode")
    private String responseCode;

    @JsonProperty("Message")
    private String message;

    public IpnResponse(String responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
