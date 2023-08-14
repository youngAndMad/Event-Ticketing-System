package danekerscode.ticketservice.service.impl;

import danekerscode.ticketservice.client.EventClient;
import danekerscode.ticketservice.client.UserClient;
import danekerscode.ticketservice.exception.TicketComponentNotFoundException;
import danekerscode.ticketservice.exception.TicketNotFoundException;
import danekerscode.ticketservice.exception.TicketReturnDateExpiredException;
import danekerscode.ticketservice.model.Ticket;
import danekerscode.ticketservice.repository.TicketRepository;
import danekerscode.ticketservice.service.TicketService;
import danekerscode.ticketservice.utils.TicketEvent;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventClient eventClient;
    private final UserClient userClient;
    private final KafkaTemplate<String, TicketEvent> kafkaTemplate;

    @Override
    public List<Ticket> findUserTickets(
            Long userId
    ) {
        return ticketRepository.findAllByUserId(userId);
    }

    @Override
    @CircuitBreaker(name = "user_service_circuit_breaker", fallbackMethod = "onUserServiceError")
    public Ticket boughtTicket(Long userId, Long eventId) {
        var checkUser = userClient.findById(userId);
        var ticket = new Ticket(eventId, userId);

        sendEvent(ticket, "ticket bought successfully");

        return ticketRepository.save(ticket);
    }

    @Override
    @CircuitBreaker(name = "event_service_circuit_breaker", fallbackMethod = "onError")
    public boolean returnTicket(Long id) {
        var ticket = ticketRepository.findById(id)
                .orElseThrow(TicketNotFoundException::new);

        var event = eventClient.getById(ticket.getEventId());

        if (event.time().isAfter(LocalDate.now().minusDays(1))) {
            throw new TicketReturnDateExpiredException();
        }

        sendEvent(ticket, "ticket return successfully");

        ticketRepository.deleteById(id);
        return true;
    }

    private void sendEvent(Ticket ticket, String status) {
        log.info("ticked event was send userId: {} eventId: {} , status {}", ticket.getUserId(), ticket.getEventId(), status);
        kafkaTemplate.send(
                "ticket_topic",
                new TicketEvent(ticket.getCode(), status, ticket.getEventId())
        );
    }

    boolean onEventServiceError(Throwable e) {
        log.error("error from event service: {}", e.getMessage());
        throw new TicketComponentNotFoundException("event");
    }

    Ticket onUserServiceError(Throwable e) {
        log.error("error from user service: {}", e.getMessage());
        throw new TicketComponentNotFoundException("user");
    }
}
