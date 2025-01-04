package com.railgo.domain.payment.model;

import com.railgo.domain.payment.type.PaymentStatus;
import com.railgo.domain.utils.model.BaseModel;
import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class Payment extends BaseModel {
    private Id id;
    private Id transactionId;
    private String paymentGateway;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private Money amount;
    private String ipAddress;
    private String userAgent;
    private PaymentStatus status;

    public Payment() {}
    public Payment(String id, String transactionId, String paymentGateway, String paymentMethod, LocalDateTime paymentTime, Money amount, String ipAddress, String userAgent, String status) {
        this.id = new Id(id);
        this.transactionId = new Id(transactionId);
        this.paymentGateway = paymentGateway;
        this.paymentMethod = paymentMethod;
        this.paymentTime = paymentTime;
        this.amount = amount;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.status = PaymentStatus.valueOf(status);
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getTransactionId() {
        return transactionId.getValue();
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = new Id(transactionId);
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = PaymentStatus.valueOf(status);
    }
}
