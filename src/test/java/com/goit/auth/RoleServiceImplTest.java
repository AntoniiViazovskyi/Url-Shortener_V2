package com.goit.auth;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void testFindByName_WhenRoleExists() {
        String roleName = "ROLE_USER";
        Role expectedRole = new Role(roleName);
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(expectedRole));

        Role actualRole = roleService.findByName(roleName);

        assertEquals(expectedRole, actualRole);
    }

    @Test
    public void testFindByName_WhenRoleDoesNotExist() {
        String roleName = "NON_EXISTENT_ROLE";
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        Role actualRole = roleService.findByName(roleName);

        assertEquals(null, actualRole);
    }
}
