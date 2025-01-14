package com.airline.booking.infrastructure.persistence.payment.mapper;

import com.airline.booking.domain.payment.model.Payment;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import com.airline.booking.infrastructure.mapper.MapperEntity;
import com.airline.booking.infrastructure.persistence.payment.model.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface PaymentMapper extends MapperEntity<PaymentEntity, Payment> {
}
