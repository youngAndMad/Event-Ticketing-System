package danekerscode.ticketservice.controller;

import danekerscode.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
