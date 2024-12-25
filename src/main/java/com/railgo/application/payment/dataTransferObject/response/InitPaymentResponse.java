package com.railgo.application.payment.dataTransferObject.response;

public class InitPaymentResponse {
    private String paymentUrl;

    public InitPaymentResponse(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
