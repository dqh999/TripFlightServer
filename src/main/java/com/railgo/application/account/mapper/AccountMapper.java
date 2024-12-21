package com.railgo.application.account.mapper;


import com.railgo.application.account.dataTransferObject.AccountDTO;
import com.railgo.application.account.dataTransferObject.request.RegisterRequest;
import com.railgo.domain.account.model.User;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.security.UserDetail;
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
