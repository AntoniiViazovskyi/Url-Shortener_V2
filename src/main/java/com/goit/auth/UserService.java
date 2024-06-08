package com.goit.auth;

import com.goit.exception.exceptions.userExceptions.UserAlreadyExistException;
import com.goit.exception.exceptions.userExceptions.UserNotFoundException;

import java.util.List;

public interface UserService {

    UserDto createUser(String email, String rawPassword) throws UserAlreadyExistException;
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    User getByEmail(String email) throws UserNotFoundException;
    UserDto updateUser(Long id, UserDto userDto);
    boolean deleteUser(Long id);
    boolean existsByEmail(String email);
}
