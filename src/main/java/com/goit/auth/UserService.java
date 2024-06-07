package com.goit.auth;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDTO, String rawPassword);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    User findByEmail(String email);
    UserDto updateUser(Long id, UserDto userDto);
    boolean deleteUser(Long id);
    boolean existsByEmail(String email);
}
