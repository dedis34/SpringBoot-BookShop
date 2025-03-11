package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserRegistrationRequestDto;
import org.example.dto.UserResponseDto;
import org.example.exception.RegistrationException;
import org.example.model.User;
import org.example.repository.user.UserRepository;
import org.example.service.UserService;
import org.example.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto) {
        if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
            throw new RegistrationException("Email already used.");
        }

        User newUser = userMapper.toModel(userRegistrationRequestDto);

        userRepository.save(newUser);

        return userMapper.toDto(newUser);
    }
}
