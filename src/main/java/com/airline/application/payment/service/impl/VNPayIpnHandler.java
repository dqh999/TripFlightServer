package com.airline.application.payment.service.impl;

import com.airline.application.payment.constant.VNPayIpnResponseConst;
import com.airline.application.payment.constant.VNPayParams;
import com.airline.application.payment.dataTransferObject.response.IpnResponse;
import com.airline.application.payment.service.IpnHandler;
import com.airline.application.ticket.service.ITicketUseCase;
import com.airline.domain.payment.model.Payment;
import com.airline.domain.payment.service.IPaymentService;
import com.airline.domain.utils.exception.BusinessException;
import com.airline.application.component.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.airline.domain.ticket.exception.TicketExceptionCode.TICKET_NOT_FOUND;

@Service
public class VNPayIpnHandler implements IpnHandler {
    private static final Logger logger = LoggerFactory.getLogger(VNPayIpnHandler.class);

    private final VNPayGatewayServiceImpl vnPayService;
    private final ITicketUseCase ticketUseCase;
    private final IPaymentService paymentService;
    private final KafkaProducer kafkaProducer;

    public VNPayIpnHandler(VNPayGatewayServiceImpl vnPayService,
                           ITicketUseCase ticketUseCase,
                           IPaymentService paymentService,
                           KafkaProducer kafkaProducer) {
        this.vnPayService = vnPayService;
        this.ticketUseCase = ticketUseCase;
        this.paymentService = paymentService;
        this.kafkaProducer = kafkaProducer;
    }

    @Value("${spring.kafka.topic.ticket.ticket-cancel}")
    private String ticketCancelTopic;

    @Override
    public IpnResponse process(Map<String, String> params) {
        if (!vnPayService.verifyIpn(params)) {
            logger.error("IPN signature verification failed for params: {}", params);
            return VNPayIpnResponseConst.SIGNATURE_FAILED;
        }

        IpnResponse response;

        String responseCode = params.get(VNPayParams.RESPONSE_CODE);
        var txnRef = params.get(VNPayParams.TXN_REF);
        if (!responseCode.equals("00")) {
            logger.warn("Received an invalid response code: {} for params: {}", responseCode, params);
            kafkaProducer.send(ticketCancelTopic, txnRef);
            return VNPayIpnResponseConst.UNKNOWN_ERROR;
        }
        try {
            var ticket = ticketUseCase.finalizePayment(txnRef);
            Payment payment = paymentService.getPayment(ticket.getPaymentId());
            paymentService.confirmPayment(payment);
            response = VNPayIpnResponseConst.SUCCESS;
        } catch (BusinessException e) {
            logger.error("BusinessException occurred: {}", e.getMessage(), e);
            switch (e.getExceptionCode()) {
                case TICKET_NOT_FOUND -> response = VNPayIpnResponseConst.ORDER_NOT_FOUND;
                default -> response = VNPayIpnResponseConst.UNKNOWN_ERROR;
            }
        } catch (Exception e) {
            logger.error("Unexpected error occurred during IPN processing: {}", e.getMessage(), e);
            response = VNPayIpnResponseConst.UNKNOWN_ERROR;
        }
        logger.info("IPN processing completed with response: {}", response);
        return response;
    }
}
