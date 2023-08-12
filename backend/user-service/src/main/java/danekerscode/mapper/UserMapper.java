package danekerscode.mapper;

import danekerscode.dto.UserDTO;
import danekerscode.model.User;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toModel(UserDTO dto);

    @Mapping(target = "id", ignore = true)
    User update(UserDTO dto , @MappingTarget User user);
}
