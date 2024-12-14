package com.example.railgo.application.account.mapper;


import com.example.railgo.application.account.dataTransferObject.request.RegisterRequest;
import com.example.railgo.domain.account.model.User;
import com.example.railgo.infrastucture.mapper.GlobalMapperConfig;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AccountMapper {
    User toDTO(RegisterRequest request);
}
