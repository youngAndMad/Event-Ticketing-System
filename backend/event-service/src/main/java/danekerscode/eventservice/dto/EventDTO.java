package danekerscode.eventservice.dto;

import danekerscode.eventservice.enums.EventType;

import java.time.LocalDate;


public record EventDTO(
        LocalDate time,
        AddressDTO address,
        EventType type,
        String title,
        String description
) {
}
