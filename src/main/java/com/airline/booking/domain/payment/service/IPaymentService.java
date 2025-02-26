package com.airline.booking.domain.payment.service;

import com.airline.booking.domain.payment.model.Payment;

import java.util.List;

public interface IPaymentService {
    void pay(Payment payment);
    void confirmPayment(Payment payment);
    void cancelPayment(Payment payment);
    List<Payment> getFailedPayments();
    Payment getPayment(String paymentId);
}
