package graph.railgo.infrastructure.persistence.ticket.mapper;

import graph.railgo.domain.ticket.model.Ticket;
import graph.railgo.infrastructure.config.GlobalMapperConfig;
import graph.railgo.infrastructure.mapper.MapperEntity;
import graph.railgo.infrastructure.persistence.ticket.model.TicketEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TicketEntityMapper extends MapperEntity<TicketEntity, Ticket> {
}
