package danekerscode.eventservice.service;

import danekerscode.eventservice.dto.EventDTO;
import danekerscode.eventservice.model.Event;

import java.util.List;

public interface EventService {
    Event save(EventDTO dto);

    Event findById(Long id);

    void delete(Long id);

    List<Event> findAll();
}
