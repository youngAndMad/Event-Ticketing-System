package danekerscode.ticketservice.utils;

import java.time.LocalDate;

public record Event(
        Long id,
        LocalDate time,
        String type,
        String title,
        String description
) {
}
