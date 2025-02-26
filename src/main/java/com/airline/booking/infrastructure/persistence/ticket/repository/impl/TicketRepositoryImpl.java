package com.airline.booking.infrastructure.persistence.ticket.repository.impl;

import com.airline.booking.domain.ticket.model.Ticket;
import com.airline.booking.domain.ticket.repository.TicketRepository;
import com.airline.booking.infrastructure.persistence.ticket.mapper.TicketEntityMapper;
import com.airline.booking.infrastructure.persistence.ticket.model.TicketEntity;
import com.airline.booking.infrastructure.persistence.ticket.repository.TicketEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public Ticket save(Ticket ticket) {
        TicketEntity ticketEntity = mapper.toEntity(ticket);
        TicketEntity savedTicket = repository.save(ticketEntity);
        return mapper.toDTO(savedTicket);
    }

    @Override
    public Optional<Ticket> findById(String id) {
        return repository.findById(id).map(mapper::toDTO);
    }
}
