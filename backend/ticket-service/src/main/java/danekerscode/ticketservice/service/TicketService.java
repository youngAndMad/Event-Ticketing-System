package danekerscode.ticketservice.service;

import danekerscode.ticketservice.model.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> findUserTickets(Long userId);

    Ticket boughtTicket(Long userId , Long eventId);

}
