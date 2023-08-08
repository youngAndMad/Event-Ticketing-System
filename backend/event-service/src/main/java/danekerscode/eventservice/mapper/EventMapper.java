package danekerscode.eventservice.mapper;

import danekerscode.eventservice.dto.AddressDTO;
import danekerscode.eventservice.dto.EventDTO;
import danekerscode.eventservice.model.Event;
import org.mapstruct.*;

@Mapper(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = AddressMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface EventMapper {

    Event toModel(EventDTO dto);

}
