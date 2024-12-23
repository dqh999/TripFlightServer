package com.railgo.infrastructure.persistence.ticket.repository;

import com.railgo.infrastructure.persistence.ticket.model.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerEntityRepository extends JpaRepository<PassengerEntity, String> {
}
