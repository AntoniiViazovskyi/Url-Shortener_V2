package com.goit.response;

import com.goit.auth.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class UserResponseTest {

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String email = "john@example.com";
        Set<Role> roles = new HashSet<>();

        UserResponse userResponse = new UserResponse(id, email, roles);

        Assertions.assertEquals(id, userResponse.getId());
        Assertions.assertEquals(email, userResponse.getEmail());
        Assertions.assertEquals(roles, userResponse.getRoles());
    }
}
