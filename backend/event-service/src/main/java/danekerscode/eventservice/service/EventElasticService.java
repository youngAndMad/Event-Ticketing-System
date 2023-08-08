package danekerscode.eventservice.service;

import danekerscode.eventservice.dto.AddressSearchRequest;
import danekerscode.eventservice.dto.EventSearchRequest;
import danekerscode.eventservice.model.Event;

import java.util.List;

public interface EventElasticService {

    List<Event> search(EventSearchRequest search , int from , int to);

    void addIndex(Event event);

    void deleteIndex(Long eventId);

    void updateIndex(Event updatedEvent);
}
