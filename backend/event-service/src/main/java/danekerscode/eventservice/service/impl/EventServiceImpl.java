package danekerscode.eventservice.service.impl;

import danekerscode.eventservice.dto.EventDTO;
import danekerscode.eventservice.exception.NotFoundException;
import danekerscode.eventservice.mapper.EventMapper;
import danekerscode.eventservice.model.Event;
import danekerscode.eventservice.repository.EventRepository;
import danekerscode.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Event save(EventDTO dto) {
        return eventRepository
                .save(eventMapper.toModel(dto));
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Event.class));
    }

    @Override
    public void delete(Long id) {
        eventRepository.delete(this.findById(id));
    }


}
