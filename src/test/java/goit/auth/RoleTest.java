package com.goit.auth;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void testRoleConstructor() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", role.getName());
    }

    @Test
    public void testSetName() {
        Role role = new Role();
        role.setName("ROLE_USER");
        assertEquals("ROLE_USER", role.getName());
    }
}