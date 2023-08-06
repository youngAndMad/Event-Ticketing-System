package danekerscode.eventservice.mapper;

import danekerscode.eventservice.model.Event;
import org.mapstruct.Mapper;

@Mapper
public interface EventMapper {

    Event toModel();
}
