package com.goit.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByName() {
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        Optional<Role> foundRole = roleRepository.findByName("ROLE_USER");
        assertTrue(foundRole.isPresent());
        assertEquals("ROLE_USER", foundRole.get().getName());
    }

    @Test
    public void testRoleNotFound() {
        Optional<Role> foundRole = roleRepository.findByName("ROLE_ADMIN");
        assertFalse(foundRole.isPresent());
    }
}