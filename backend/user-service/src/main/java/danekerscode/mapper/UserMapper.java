package danekerscode.mapper;

import danekerscode.dto.UserDTO;
import danekerscode.model.User;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {

    User toModel(UserDTO dto);

    User update(UserDTO dto , @MappingTarget User user);
}
