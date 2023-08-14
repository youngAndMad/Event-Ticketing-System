package danekerscode.eventservice.mapper;

import danekerscode.eventservice.dto.AddressDTO;
import danekerscode.eventservice.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AddressMapper {
    @Mapping(target = "id" , ignore = true)
    Address toModel(AddressDTO dto);
}
