package com.railgo.application.payment.dataTransferObject.response;

import java.time.LocalDateTime;

public class InitPaymentResponse {
    private String paymentId;
    private String paymentGateway;
    private String paymentUrl;
    private LocalDateTime expiryTime;

    public InitPaymentResponse(String paymentId,
                               String paymentGateway,
                               String paymentUrl,
                               LocalDateTime expiryTime) {
        this.paymentId = paymentId;
        this.paymentGateway = paymentGateway;
        this.paymentUrl = paymentUrl;
        this.expiryTime = expiryTime;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
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
