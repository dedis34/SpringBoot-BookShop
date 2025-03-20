package org.example.dto.user;

import lombok.Data;
import java.util.Set;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private Set<String> roles;
}
