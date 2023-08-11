package danekerscode.eventservice.utils;

import danekerscode.eventservice.enums.EventType;

import java.time.LocalDate;

public record EventIndex(
        Long eventId,
        Long addressId,
        LocalDate time,
        String country,
        String city,
        String street,
        String buildingName,
        String mark,
        EventType type,
        String title,
        String description
) {
}
