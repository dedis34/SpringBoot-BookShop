package org.example.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailService {
    UserDetails loadUserByUsername(String email);
}
