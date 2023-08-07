package danekerscode.eventservice.controller;

import danekerscode.eventservice.dto.EventDTO;
import danekerscode.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("event")
public class EventController {

    private final EventService eventService;

    @PostMapping
    ResponseEntity<?> save(
            @RequestBody EventDTO eventDTO
    ) {
        return ResponseEntity.status(CREATED)
                .body(eventService.save(eventDTO));
    }

    @GetMapping("{id}")
    ResponseEntity<?> get(
            @PathVariable Long id
    ){
        return ResponseEntity
                .ok(eventService.findById(id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    void delete(
            @PathVariable Long id
    ){
        eventService.delete(id);
    }
}
