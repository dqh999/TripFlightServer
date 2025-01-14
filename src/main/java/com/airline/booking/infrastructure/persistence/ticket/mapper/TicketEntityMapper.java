package com.airline.booking.infrastructure.persistence.ticket.mapper;

import com.airline.booking.domain.ticket.model.Ticket;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import com.airline.booking.infrastructure.mapper.MapperEntity;
import com.airline.booking.infrastructure.persistence.ticket.model.TicketEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TicketEntityMapper extends MapperEntity<TicketEntity, Ticket> {
}
