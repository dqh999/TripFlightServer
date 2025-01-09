package com.flight.server.application.ticket.mapper;

import com.flight.server.application.ticket.dataTransferObject.response.TicketResponse;
import com.flight.server.domain.ticket.model.Ticket;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TicketMapper extends MapperEntity<Ticket, TicketResponse> {
    TicketResponse toDTO(Ticket ticket);
}
