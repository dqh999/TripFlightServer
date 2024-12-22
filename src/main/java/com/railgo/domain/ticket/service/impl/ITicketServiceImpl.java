package com.railgo.domain.ticket.service.impl;

import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.ticket.repository.TicketRepository;
import com.railgo.domain.ticket.service.ITicketPricingService;
import com.railgo.domain.ticket.service.ITicketService;
import com.railgo.domain.train.model.Train;
import com.railgo.domain.utils.type.Currency;
import com.railgo.domain.utils.valueObject.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ITicketServiceImpl implements ITicketService, ITicketPricingService {
    private final TicketRepository ticketRepository;
    @Autowired
    public ITicketServiceImpl(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket book(Ticket ticket) {
        ticketRepository.save(ticket);
        return ticket;
    }



    @Override
    public Money calculateTicketPrice(Train train, StationRoute stationRoute) {
        BigDecimal baseFarePerKm  = BigDecimal.valueOf(500).multiply(train.getRatePerKm());

        Double distance = stationRoute.getDistanceKm();
        BigDecimal ticketPrice = baseFarePerKm.multiply(BigDecimal.valueOf(distance));

        return new Money(ticketPrice, Currency.VND.getValue());
    }
}
