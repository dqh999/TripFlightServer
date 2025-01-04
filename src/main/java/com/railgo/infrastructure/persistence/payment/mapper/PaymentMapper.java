package com.railgo.infrastructure.persistence.payment.mapper;

import com.railgo.domain.payment.model.Payment;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import com.railgo.infrastructure.persistence.payment.model.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface PaymentMapper extends MapperEntity<PaymentEntity, Payment> {
}
