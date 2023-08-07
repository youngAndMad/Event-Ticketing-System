package danekerscode.eventservice.dto;

import danekerscode.eventservice.enums.EventType;

import java.time.LocalDateTime;

public record EventDTO(
        LocalDateTime time,
        AddressDTO address,
        EventType type,
        String title,
        String description
) {
}
