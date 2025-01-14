package com.airline.booking.application.payment.service;

import com.airline.booking.application.payment.dataTransferObject.request.InitPaymentRequest;
import com.airline.booking.application.payment.dataTransferObject.response.InitPaymentResponse;

public interface IPaymentGatewayService {
    InitPaymentResponse init(InitPaymentRequest request);
}
