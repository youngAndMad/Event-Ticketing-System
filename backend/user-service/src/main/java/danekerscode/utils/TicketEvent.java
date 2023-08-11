package danekerscode.utils;

public record TicketEvent(
        String code,
        String status,
        Long userId
) {
}