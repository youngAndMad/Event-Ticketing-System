package danekerscode.eventservice.mapper;

import danekerscode.eventservice.dto.AddressDTO;
import danekerscode.eventservice.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AddressMapper {
    Address toModel(AddressDTO dto);
}
