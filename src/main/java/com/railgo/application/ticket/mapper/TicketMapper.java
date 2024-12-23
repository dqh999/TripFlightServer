package com.railgo.application.ticket.mapper;

import com.railgo.application.ticket.dataTransferObject.PassengerDTO;
import com.railgo.application.ticket.dataTransferObject.response.TicketResponse;
import com.railgo.domain.ticket.model.Passenger;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TicketMapper extends MapperEntity<Ticket, TicketResponse> {
    List<Passenger> toPassengers(List<PassengerDTO> passengerDTOs);
}
