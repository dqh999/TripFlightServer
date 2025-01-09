package com.flight.server.application.payment.service;

import com.flight.server.application.payment.dataTransferObject.request.InitPaymentRequest;
import com.flight.server.application.payment.dataTransferObject.response.InitPaymentResponse;

public interface IPaymentGatewayService {
    InitPaymentResponse init(InitPaymentRequest request);
}
