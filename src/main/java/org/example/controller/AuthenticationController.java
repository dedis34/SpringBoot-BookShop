package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.user.UserLoginRequestDto;
import org.example.dto.user.UserLoginResponseDto;
import org.example.dto.user.UserRegistrationRequestDto;
import org.example.dto.user.UserResponseDto;
import org.example.security.service.AuthenticationService;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User management", description = "Endpoints for managing users")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user", description = "Register a new user")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request) {
        return userService.register(request);
    }

    @Operation(summary = "log user to the app", description = "logging user to the app")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
