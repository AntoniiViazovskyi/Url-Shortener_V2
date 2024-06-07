package com.goit.auth;

public class UserMapper {
    public static UserDto toDTO(User user) {
        return new UserDto(user.getId(), user.getEmail());
    }

    public static User toEntity(UserDto userDTO) {
        return new User(userDTO.getEmail(), userDTO.getPassword());
    }
}
