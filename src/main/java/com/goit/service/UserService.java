package com.goit.service;


import com.goit.dto.CreateUserDto;
import com.goit.entities.User;

public interface UserService {

    void createUser(CreateUserDto createUserDto);
    boolean existsByEmail(String email);

    User findByEmail(String email);

    void save(User user);

}
