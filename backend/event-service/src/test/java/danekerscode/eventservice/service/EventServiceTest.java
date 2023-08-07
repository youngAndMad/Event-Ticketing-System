package danekerscode.eventservice.service;

import danekerscode.eventservice.model.Event;
import danekerscode.eventservice.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
     EventRepository eventRepository;

    @InjectMocks
     EventService eventService;

    @Test
    @DisplayName("Should return null when the id does not exist")
    void findByIdWhenIdDoesNotExist() {
        Long nonExistingId = 1L;
        when(eventRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Event result = eventService.findById(nonExistingId);

        assertNull(result);
        verify(eventRepository, times(1)).findById(nonExistingId);
    }

    @Test
    @DisplayName("Should return the event when the id exists")
    void findByIdWhenIdExists() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setTitle("Test Event");
        event.setDescription("This is a test event");

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Event result = eventService.findById(eventId);

        assertNotNull(result);
        assertEquals(eventId, result.getId());
        assertEquals("Test Event", result.getTitle());
        assertEquals("This is a test event", result.getDescription());

        verify(eventRepository, times(1)).findById(eventId);
    }

}