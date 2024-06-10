package com.goit.auth;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    @Test
    public void testToDTO() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        UserDto userDto = UserMapper.toDTO(user);

        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
        assertEquals("test@example.com", userDto.getEmail());
    }

    @Test
    public void testToEntity() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");

        User user = UserMapper.toEntity(userDto);

        assertNotNull(user);
        assertNull(user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
    }
}