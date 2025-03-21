package org.example.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.customAnnotations.FieldMatch;
import org.example.customAnnotations.UniqueEmail;

@Data
@FieldMatch(field = "password", fieldMatch = "repeatPassword", message = "Passwords must match")
public class UserRegistrationRequestDto {
    @NotNull
    @NotBlank
    @UniqueEmail
    private String email;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String repeatPassword;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotBlank
    private String shippingAddress;
}
