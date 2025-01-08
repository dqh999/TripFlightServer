package com.airline.application.account.mapper;


import com.airline.application.account.dataTransferObject.AccountDTO;
import com.airline.application.account.dataTransferObject.request.RegisterRequest;
import com.airline.domain.account.model.User;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.security.UserDetail;
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
