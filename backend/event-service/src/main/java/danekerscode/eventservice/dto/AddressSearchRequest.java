package danekerscode.eventservice.dto;

public record AddressSearchRequest(
        String country,
        String city
) {
}
