package danekerscode.ticketservice.controller;

import danekerscode.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("ticket")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("{id}")
    ResponseEntity<?> find(
            @PathVariable Long id
    ){
        return ResponseEntity
                .ok(ticketService.findUserTickets(id));
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete(
            @PathVariable Long id
    ){
        return ResponseEntity
                .ok(ticketService.returnTicket(id));
    }

    @PostMapping
    ResponseEntity<?> boughtTicket(
        @RequestParam Long userId,
        @RequestParam Long eventId
    ){
        return ResponseEntity.status(201)
                .body(ticketService.boughtTicket(userId, eventId));
    }
}
