package danekerscode.eventservice.mapper;

import danekerscode.eventservice.dto.EventDTO;
import danekerscode.eventservice.model.Event;
import danekerscode.eventservice.utils.EventIndex;
import org.mapstruct.*;


@Mapper(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = AddressMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface EventMapper {

    @Mapping(target = "id" , ignore = true)
    Event toModel(EventDTO dto);

    @Mapping(target = "eventId" , expression = "java(event.getId())")
    @Mapping(target = "addressId" , expression = "java(event.getAddress().getId())")
    @Mapping(target = "mark" , expression = "java(event.getAddress().getMark())")
    @Mapping(target = "country" , expression = "java(event.getAddress().getCountry())")
    @Mapping(target = "buildingName" , expression = "java(event.getAddress().getBuildingName())")
    @Mapping(target = "street" , expression = "java(event.getAddress().getStreet())")
    @Mapping(target = "city" , expression = "java(event.getAddress().getCity())")
    EventIndex toIndex(Event event);

}
