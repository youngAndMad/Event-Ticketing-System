package danekerscode.eventservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EventSearchRequest(
        AddressSearchRequest address,
        String text,
        LocalDateTime time
) {
}
