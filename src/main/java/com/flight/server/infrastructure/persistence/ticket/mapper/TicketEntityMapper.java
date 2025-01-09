package com.flight.server.infrastructure.persistence.ticket.mapper;

import com.flight.server.domain.ticket.model.Ticket;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.mapper.MapperEntity;
import com.flight.server.infrastructure.persistence.ticket.model.TicketEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TicketEntityMapper extends MapperEntity<TicketEntity, Ticket> {
}
