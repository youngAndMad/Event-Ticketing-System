package danekerscode.ticketservice.service.impl;

import danekerscode.ticketservice.client.EventClient;
import danekerscode.ticketservice.exception.TicketNotFoundException;
import danekerscode.ticketservice.exception.TicketReturnDateExpiredException;
import danekerscode.ticketservice.model.Ticket;
import danekerscode.ticketservice.repository.TicketRepository;
import danekerscode.ticketservice.service.TicketService;
import danekerscode.ticketservice.utils.TicketEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static danekerscode.ticketservice.utils.AppConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventClient eventClient;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public List<Ticket> findUserTickets(
            Long userId
    ) {
        return ticketRepository.findAllByUserId(userId);
    }

    @Override
    public Ticket boughtTicket(Long userId, Long eventId) {
        var ticket = new Ticket(eventId, userId);

        rabbitTemplate.convertAndSend(
                TICKET_EXCHANGE,
                TICKET_ROUTING_KEY,
                new TicketEvent(ticket.getCode(), STATUS_BOUGHT, userId)
        );
        log.info("ticked event was send userId: {} eventId: {}" , userId , eventId);

        return ticketRepository.save(ticket);
    }

    @Override
    public boolean returnTicket(Long id) {
        var ticket = ticketRepository.findById(id)
                .orElseThrow(TicketNotFoundException::new);

        var event = eventClient.getById(ticket.getEventId());

        if (event.time().isAfter(LocalDate.now().minusDays(1))) {
            throw new TicketReturnDateExpiredException();
        }

        rabbitTemplate.convertAndSend(
                TICKET_EXCHANGE,
                TICKET_ROUTING_KEY,
                new TicketEvent(ticket.getCode(), STATUS_RETURNED, ticket.getEventId())
        );

        ticketRepository.deleteById(id);
        return true;
    }
}
