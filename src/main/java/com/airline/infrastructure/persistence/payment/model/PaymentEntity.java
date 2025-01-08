package com.railgo.infrastructure.persistence.payment.model;

import com.railgo.domain.utils.valueObject.Money;
import com.railgo.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_payments")
public class PaymentEntity extends BaseEntity {
    @Id
    private String id;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "payment_gateway")
    private String paymentGateway;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "payment_time")
    private LocalDateTime paymentTime;
    private BigDecimal amount;
    private String currency;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "user_agent")
    private String userAgent;
    private String status;

    public PaymentEntity() {}
    public PaymentEntity(String id, String transactionId, String paymentGateway, String paymentMethod, LocalDateTime paymentTime, Money amount, String ipAddress, String userAgent, String status) {
        this.id = id;
        this.transactionId = transactionId;
        this.paymentGateway = paymentGateway;
        this.paymentMethod = paymentMethod;
        this.paymentTime = paymentTime;
        this.amount = amount.getValue();
        this.currency = amount.getCurrency();
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
        return new Money(this.amount, this.currency);
    }

    public void setAmount(Money amount) {
        this.amount = amount.getValue();
        this.currency = amount.getCurrency();
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
