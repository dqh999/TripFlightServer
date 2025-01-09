package com.flight.server.application.account.mapper;


import com.flight.server.application.account.dataTransferObject.AccountDTO;
import com.flight.server.application.account.dataTransferObject.request.RegisterRequest;
import com.flight.server.domain.account.model.User;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.security.UserDetail;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AccountMapper {
    User toDTO(RegisterRequest request);

    @Mapping(source = "phoneNumber",target = "username")
    UserDetail toEntity(User user);

    @Mapping(source = "phoneNumber",target = "username")
    UserDetail toEntity(AccountDTO accountDTO);
}
