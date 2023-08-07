package danekerscode.eventservice.service;

import danekerscode.eventservice.dto.EventDTO;
import danekerscode.eventservice.model.Event;

public interface EventService {
    Event save(EventDTO dto);

    Event findById(Long id);

    void delete(Long id);
}
