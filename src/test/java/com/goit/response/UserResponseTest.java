package com.goit.response;

import com.goit.auth.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class UserResponseTest {

    @Test
    void testNoArgsConstructor() {
        UserResponse userResponse = new UserResponse();
        Assertions.assertNotNull(userResponse);
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String username = "john_doe";
        String email = "john@example.com";
        Set<Role> roles = new HashSet<>();

        UserResponse userResponse = new UserResponse(id, username, email, roles);

        Assertions.assertEquals(id, userResponse.getId());
        Assertions.assertEquals(username, userResponse.getUsername());
        Assertions.assertEquals(email, userResponse.getEmail());
        Assertions.assertEquals(roles, userResponse.getRoles());
    }

    @Test
    void testGettersAndSetters() {
        UserResponse userResponse = new UserResponse();

        Long id = 1L;
        userResponse.setId(id);
        String username = "john_doe";
        userResponse.setUsername(username);
        String email = "john@example.com";
        userResponse.setEmail(email);
        Set<Role> roles = new HashSet<>();
        userResponse.setRoles(roles);

        Assertions.assertEquals(id, userResponse.getId());
        Assertions.assertEquals(username, userResponse.getUsername());
        Assertions.assertEquals(email, userResponse.getEmail());
        Assertions.assertEquals(roles, userResponse.getRoles());
    }
}
