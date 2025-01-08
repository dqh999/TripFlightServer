package com.airline.infrastructure.persistence.ticket.mapper;

import com.airline.domain.ticket.model.Ticket;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.mapper.MapperEntity;
import com.airline.infrastructure.persistence.ticket.model.TicketEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TicketEntityMapper extends MapperEntity<TicketEntity, Ticket> {
}
