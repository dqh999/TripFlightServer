package com.airline.booking.infrastructure.persistence.ticket.repository;

import com.airline.booking.infrastructure.persistence.ticket.model.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketEntityRepository extends JpaRepository<TicketEntity, String> {
    Optional<TicketEntity> findByPaymentId(String paymentId);
}
