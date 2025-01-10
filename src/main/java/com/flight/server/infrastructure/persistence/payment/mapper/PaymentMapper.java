package com.flight.server.infrastructure.persistence.payment.mapper;

import com.flight.server.domain.payment.model.Payment;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.mapper.MapperEntity;
import com.flight.server.infrastructure.persistence.payment.model.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface PaymentMapper extends MapperEntity<PaymentEntity, Payment> {
}
