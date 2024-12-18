package graph.railgo.infrastructure.persistence.ticket.repository;

import graph.railgo.infrastructure.persistence.ticket.model.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketEntityRepository extends JpaRepository<TicketEntity, String> {
}
