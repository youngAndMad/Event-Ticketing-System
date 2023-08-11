package danekerscode.ticketservice.service.impl;

import danekerscode.ticketservice.model.Ticket;
import danekerscode.ticketservice.repository.TicketRepository;
import danekerscode.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> findUserTickets(
            Long userId
    ) {
        return ticketRepository.findAllByUserId(userId);
    }

    @Override
    public Ticket boughtTicket(Long userId, Long eventId) {
        return null;
    }
}
