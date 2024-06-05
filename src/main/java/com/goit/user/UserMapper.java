package com.goit.user;

public class UserMapper {
    public static UserDto toDTO(User user) {
        return new UserDto(user.getId(), user.getUsername());
    }

    public static User toEntity(UserDto userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword());
    }
}
