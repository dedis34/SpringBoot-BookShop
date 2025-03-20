package org.example.mapper;

import org.example.config.MapperConfig;
import org.example.dto.user.UserRegistrationRequestDto;
import org.example.dto.user.UserResponseDto;
import org.example.model.Role;
import org.example.model.User;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);
    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);

    default Set<String> mapRolesToRoleNames(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toSet());
    }
}

