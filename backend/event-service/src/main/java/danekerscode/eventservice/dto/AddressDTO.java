package danekerscode.eventservice.dto;

public record AddressDTO(
        String country,
        String city,
        String street,
        String buildingName,
        String mark
) {
}
