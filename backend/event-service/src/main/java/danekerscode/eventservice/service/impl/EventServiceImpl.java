package danekerscode.eventservice.service.impl;

import danekerscode.eventservice.dto.EventDTO;
import danekerscode.eventservice.exception.NotFoundException;
import danekerscode.eventservice.mapper.EventMapper;
import danekerscode.eventservice.model.Event;
import danekerscode.eventservice.repository.EventRepository;
import danekerscode.eventservice.service.EventElasticService;
import danekerscode.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EventElasticService elastic;

    @Override
    public Event save(EventDTO dto) {
        var event = eventMapper.toModel(dto);
        elastic.addIndex(event);
        return eventRepository
                .save(event);
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Event.class));
    }

    @Override
    public void delete(Long id) {
        eventRepository.delete(this.findById(id));
        elastic.deleteIndex(id);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }


}
