package org.example.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.user.UserLoginRequestDto;
import org.example.dto.user.UserLoginResponseDto;
import org.example.security.service.AuthenticationService;
import org.example.security.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto request) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        String token = jwtUtil.generateToken(authenticate.getName());

        return new UserLoginResponseDto(token);
    }
}
