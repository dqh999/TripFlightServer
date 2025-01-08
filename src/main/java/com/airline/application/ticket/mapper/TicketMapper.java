package com.airline.application.ticket.mapper;

import com.airline.application.ticket.dataTransferObject.response.TicketResponse;
import com.airline.domain.ticket.model.Ticket;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TicketMapper extends MapperEntity<Ticket, TicketResponse> {
    TicketResponse toDTO(Ticket ticket);
}
