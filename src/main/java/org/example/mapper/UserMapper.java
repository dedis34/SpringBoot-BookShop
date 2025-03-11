package org.example.mapper;

import org.example.config.MapperConfig;
import org.example.dto.UserRegistrationRequestDto;
import org.example.dto.UserResponseDto;
import org.example.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);
    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);
}
