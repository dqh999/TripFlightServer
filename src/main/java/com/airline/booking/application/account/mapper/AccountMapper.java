package com.airline.booking.application.account.mapper;


import com.airline.booking.application.account.dataTransferObject.request.RegisterRequest;
import com.airline.booking.application.account.dataTransferObject.response.UserResponse;
import com.airline.booking.domain.account.model.User;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AccountMapper {
    User toDTO(RegisterRequest request);

    UserResponse toResponse(User user);
}
