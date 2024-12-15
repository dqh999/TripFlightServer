package com.example.railgo.application.account.mapper;


import com.example.railgo.application.account.dataTransferObject.AccountDTO;
import com.example.railgo.application.account.dataTransferObject.request.RegisterRequest;
import com.example.railgo.domain.account.model.User;
import com.example.railgo.infrastructure.mapper.GlobalMapperConfig;
import com.example.railgo.infrastructure.security.UserDetail;
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
