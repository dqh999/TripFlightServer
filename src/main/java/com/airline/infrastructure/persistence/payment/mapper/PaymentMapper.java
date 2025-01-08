package com.airline.infrastructure.persistence.payment.mapper;

import com.airline.domain.payment.model.Payment;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.mapper.MapperEntity;
import com.airline.infrastructure.persistence.payment.model.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface PaymentMapper extends MapperEntity<PaymentEntity, Payment> {
}
