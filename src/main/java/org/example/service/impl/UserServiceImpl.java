package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.user.UserRegistrationRequestDto;
import org.example.dto.user.UserResponseDto;
import org.example.exception.RegistrationException;
import org.example.model.User;
import org.example.repository.user.UserRepository;
import org.example.service.UserService;
import org.example.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto) {
        if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
            throw new RegistrationException("Email already used.");
        }

        User newUser = userMapper.toModel(userRegistrationRequestDto);

        String encodedPassword = passwordEncoder.encode(userRegistrationRequestDto.getPassword());
        newUser.setPassword(encodedPassword);

        userRepository.save(newUser);

        return userMapper.toDto(newUser);
    }
}
