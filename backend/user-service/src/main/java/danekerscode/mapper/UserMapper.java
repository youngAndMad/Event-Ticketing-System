package danekerscode.mapper;

import danekerscode.dto.UserRegistrationDTO;
import danekerscode.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toModel(UserRegistrationDTO dto);
}
