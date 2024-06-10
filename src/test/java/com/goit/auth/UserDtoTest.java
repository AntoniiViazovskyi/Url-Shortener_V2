package com.goit.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {

    @Test
    public void testNoArgsConstructor() {
        UserDto userDto = new UserDto();

        assertNotNull(userDto);
        assertNull(userDto.getId());
        assertNull(userDto.getEmail());
        assertNull(userDto.getPassword());
    }

    @Test
    public void testAllArgsConstructor() {
        Long id = 1L;
        String email = "test@example.com";
        String password = "password";

        UserDto userDto = new UserDto(id, email, password);

        assertNotNull(userDto);
        assertEquals(id, userDto.getId());
        assertEquals(email, userDto.getEmail());
        assertEquals(password, userDto.getPassword());
    }

    @Test
    public void testSettersAndGetters() {
        Long id = 1L;
        String email = "test@example.com";
        String password = "password";
        UserDto userDto = new UserDto();

        userDto.setId(id);
        userDto.setEmail(email);
        userDto.setPassword(password);

        assertEquals(id, userDto.getId());
        assertEquals(email, userDto.getEmail());
        assertEquals(password, userDto.getPassword());
    }
}