package org.example.mapper;

import org.example.config.MapperConfig;
import org.example.dto.UserRegistrationRequestDto;
import org.example.dto.UserResponseDto;
import org.example.model.Role;
import org.example.model.User;
import org.mapstruct.Mapper;

import java.util.HashSet;
import java.util.Set;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);
    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);

    default Set<String> mapRolesToRoleNames(Set<Role> roles) {
        Set<String> roleNames = new HashSet<>();
        for (Role role : roles) {
            roleNames.add(role.getName().name());
        }
        return roleNames;
    }
}

