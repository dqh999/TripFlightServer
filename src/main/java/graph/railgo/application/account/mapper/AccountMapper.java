package graph.railgo.application.account.mapper;


import graph.railgo.application.account.dataTransferObject.AccountDTO;
import graph.railgo.application.account.dataTransferObject.request.RegisterRequest;
import graph.railgo.domain.account.model.User;
import graph.railgo.infrastructure.config.GlobalMapperConfig;
import graph.railgo.infrastructure.security.UserDetail;
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
