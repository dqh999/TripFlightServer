package com.railgo.application.ticket.dataTransferObject.request;

public class ApplyDiscountRequest {
    private String discountCode;

    public ApplyDiscountRequest() {
    }

    public ApplyDiscountRequest(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }
}
