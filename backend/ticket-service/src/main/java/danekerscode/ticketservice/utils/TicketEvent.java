package danekerscode.ticketservice.utils;

public record TicketEvent(
        String code,
        String status,
        Long userId
) {
}
