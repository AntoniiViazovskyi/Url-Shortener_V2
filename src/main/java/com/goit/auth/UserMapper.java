package com.goit.auth;

import com.goit.exception.LogEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserMapper {
    public static UserDto toDTO(User user) {
        log.info(String.format("%s User entity %s was mapped to UserDto", LogEnum.MAPPER, user));
        return new UserDto(user.getId(), user.getEmail());
    }

    public static User toEntity(UserDto userDTO) {
        log.info(String.format("%s UserDto %s was mapped to User entity", LogEnum.MAPPER, userDTO));
        return new User(userDTO.getEmail(), userDTO.getPassword());
    }
}
