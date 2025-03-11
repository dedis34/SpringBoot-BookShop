package org.example.service;

import org.example.dto.UserRegistrationRequestDto;
import org.example.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto);
}
