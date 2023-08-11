package danekerscode.eventservice.dto;

import danekerscode.eventservice.enums.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EventSearchRequest(
        String text,
        LocalDateTime time,
        EventType type,
        String country,
        String city
) {
}
