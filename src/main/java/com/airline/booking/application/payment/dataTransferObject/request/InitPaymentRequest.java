package com.airline.booking.application.payment.dataTransferObject.request;

import com.airline.booking.domain.utils.valueObject.Money;


public class InitPaymentRequest {
    private String ipAddress;
    private String accountId;
    private String txnRef;
    private Money totalAmount;
    public InitPaymentRequest() {}

    public InitPaymentRequest(String ipAddress, String accountId, String txnRef, Money totalAmount) {
        this.ipAddress = ipAddress;
        this.accountId = accountId;
        this.txnRef = txnRef;
        this.totalAmount = totalAmount;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTxnRef() {
        return txnRef;
    }

    public void setTxnRef(String txnRef) {
        this.txnRef = txnRef;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Money totalAmount) {
        this.totalAmount = totalAmount;
    }
}
