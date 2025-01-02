package com.railgo.application.payment.dataTransferObject.response;

import java.time.LocalDateTime;

public class InitPaymentResponse {
    private String paymentUrl;
    private LocalDateTime expiryTime;

    public InitPaymentResponse(String paymentUrl,
                               LocalDateTime expiryTime) {
        this.paymentUrl = paymentUrl;
        this.expiryTime = expiryTime;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }
}
