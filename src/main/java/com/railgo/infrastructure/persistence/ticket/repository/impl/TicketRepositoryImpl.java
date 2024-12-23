package com.railgo.infrastructure.persistence.ticket.repository.impl;

import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.ticket.repository.TicketRepository;
import com.railgo.infrastructure.persistence.ticket.mapper.TicketEntityMapper;
import com.railgo.infrastructure.persistence.ticket.model.TicketEntity;
import com.railgo.infrastructure.persistence.ticket.repository.TicketEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepositoryImpl implements TicketRepository {
    private final TicketEntityRepository repository;
    private final TicketEntityMapper mapper;

    @Autowired
    public TicketRepositoryImpl(TicketEntityRepository repository, TicketEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Ticket ticket) {
        TicketEntity ticketEntity = mapper.toEntity(ticket);
        repository.save(ticketEntity);
    }
}
