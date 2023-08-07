package danekerscode.eventservice.mapper;

import danekerscode.eventservice.dto.AddressDTO;
import danekerscode.eventservice.dto.EventDTO;
import danekerscode.eventservice.model.Event;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = AddressMapper.class,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface EventMapper {

    Event toModel(EventDTO dto);

}
