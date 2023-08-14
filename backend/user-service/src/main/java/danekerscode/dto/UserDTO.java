package danekerscode.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email String email,
        String password
) {
}
