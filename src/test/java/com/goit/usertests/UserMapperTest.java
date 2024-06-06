package com.goit.usertests;

import com.goit.user.User;
import com.goit.user.UserDto;
import com.goit.user.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        user = new User("testuser", "password");
        userDto = new UserDto(1L, "testuser");
    }

    @Test
    public void testToDto() {
        UserDto mappedUserDto = UserMapper.toDTO(user);

        assertEquals(user.getId(), mappedUserDto.getId());
        assertEquals(user.getUsername(), mappedUserDto.getUsername());
    }

    @Test
    public void testToEntity() {
        User mappedUser = UserMapper.toEntity(userDto);

        assertEquals(userDto.getUsername(), mappedUser.getUsername());
        assertEquals(userDto.getPassword(), mappedUser.getPassword());
    }
}