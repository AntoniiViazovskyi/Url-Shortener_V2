package com.goit.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testFindByName_ExistingRole() {
        String roleName = "ROLE_USER";
        Role role = new Role(roleName);
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        Role foundRole = roleService.findByName(roleName);

        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getName()).isEqualTo(roleName);
    }

    @Test
    void testFindByName_NonExistingRole() {
        String roleName = "ROLE_ADMIN";
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        Role foundRole = roleService.findByName(roleName);

        assertThat(foundRole).isNull();
    }
}
