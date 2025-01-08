package com.railgo.application.ticket.dataTransferObject.response;

import com.railgo.application.payment.dataTransferObject.response.InitPaymentResponse;

public class TicketBookResponse {
    private TicketResponse ticket;
    private InitPaymentResponse payment;

    public TicketBookResponse(TicketResponse ticket, InitPaymentResponse payment) {
        this.ticket = ticket;
        this.payment = payment;
    }

    public TicketResponse getTicket() {
        return ticket;
    }

    public void setTicket(TicketResponse ticket) {
        this.ticket = ticket;
    }

    public InitPaymentResponse getPayment() {
        return payment;
    }

    public void setPayment(InitPaymentResponse payment) {
        this.payment = payment;
    }
}
