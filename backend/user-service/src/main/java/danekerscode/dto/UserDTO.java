package danekerscode.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email String email,
        String password
) {
}
