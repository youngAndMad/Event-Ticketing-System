package danekerscode.eventservice.mapper;

import danekerscode.eventservice.model.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    Address toModel();
}
