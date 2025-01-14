package com.airline.booking.application.ticket.mapper;

import com.airline.booking.application.ticket.dataTransferObject.response.TicketResponse;
import com.airline.booking.domain.ticket.model.Ticket;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TicketMapper {
    TicketResponse toDTO(Ticket ticket);
    TicketResponse toResponse(Ticket ticket);
}
