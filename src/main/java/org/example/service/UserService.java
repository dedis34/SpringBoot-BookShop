package org.example.service;

import org.example.dto.user.UserRegistrationRequestDto;
import org.example.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto);
}
