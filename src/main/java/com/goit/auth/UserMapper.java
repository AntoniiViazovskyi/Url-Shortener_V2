package com.goit.auth;

import com.goit.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public static UserDto toDTO(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(user.getRoles()).build();
    }

    public static User toEntity(UserDto userDTO) {
        return new User(userDTO.getEmail(), userDTO.getPassword());
    }

    public UserResponse toUserResponse(UserDto userDto) {
        return UserResponse.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .roles(new HashSet<>(userDto.getRoles())).build();
    }
}
