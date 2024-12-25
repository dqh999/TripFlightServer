package com.railgo.application.payment.service.impl;

import com.railgo.application.payment.constant.VNPayIpnResponseConst;
import com.railgo.application.payment.constant.VNPayParams;
import com.railgo.application.payment.dataTransferObject.response.IpnResponse;
import com.railgo.application.payment.service.IpnHandler;
import com.railgo.application.ticket.service.ITicketUseCase;
import com.railgo.domain.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.railgo.domain.ticket.exception.TicketExceptionCode.TICKET_NOT_FOUND;

@Service
public class VNPayIpnHandler implements IpnHandler {
    private final VNPayServiceImpl vnPayService;
    private final ITicketUseCase ticketUseCase;

    public VNPayIpnHandler(VNPayServiceImpl vnPayService,
                           ITicketUseCase ticketUseCase) {
        this.vnPayService = vnPayService;
        this.ticketUseCase = ticketUseCase;
    }

    @Override
    public IpnResponse process(Map<String, String> params) {
        if (!vnPayService.verifyIpn(params)) {
            return VNPayIpnResponseConst.SIGNATURE_FAILED;
        }
        IpnResponse response;
        try {
            var txnRef = params.get(VNPayParams.TXN_REF);

            ticketUseCase.finalizePayment(txnRef);
            response = VNPayIpnResponseConst.SUCCESS;
        } catch (BusinessException e) {
            switch (e.getExceptionCode()){
                case TICKET_NOT_FOUND -> response = VNPayIpnResponseConst.ORDER_NOT_FOUND;
                default -> response = VNPayIpnResponseConst.UNKNOWN_ERROR;
            }
        } catch (Exception e) {
            response = VNPayIpnResponseConst.UNKNOWN_ERROR;
        }
        return response;
    }
}
