package com.goit.problem;

import com.goit.auth.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RoleService roleService;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPasswordEncoder() {
        userServiceImpl.passwordEncoder(null);
    }

 /*   @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(new User(1L, "email", "encodedPassword"));
        when(roleService.findByName(anyString())).thenReturn(new Role(0, "name"));
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodeResponse");

        UserDto result = userServiceImpl.createUser(new UserDto(Long.valueOf(1), "email", "password"), "rawPassword");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("email", result.getEmail());
        Assertions.assertEquals("encodedPassword", result.getPassword());
    } ///

    @Test
    void testCreateUser2() {
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(roleService.findByName(anyString())).thenReturn(new Role(0, "name"));
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodeResponse");

        UserDto result = userServiceImpl.createUser("email", "rawPassword");
        Assertions.assertEquals(new UserDto(Long.valueOf(1), "email", "password"), result);
    } ////*/

    @Test
    void testGetUserById() {
        User user = new User(Long.valueOf(1), "email", "password");
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        UserDto result = userServiceImpl.getUserById(Long.valueOf(1));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getPassword(), result.getPassword());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(new User("email", "password")));

        List<UserDto> result = userServiceImpl.getAllUsers();
        /*Assertions.assertEquals(List.of(new UserDto(Long.valueOf(1), "email", "password")), result);*/

        // Перевірка результату
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("email", result.get(0).getEmail());
        Assertions.assertEquals("password", result.get(0).getPassword());
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Act
        User result = userServiceImpl.findByEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        User user = new User();
        user.setId(1L);
        user.setEmail("old@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserDto result = userServiceImpl.updateUser(1L, userDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = userServiceImpl.deleteUser(1L);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testExistsByEmail() {
        // Arrange
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // Act
        boolean result = userServiceImpl.existsByEmail("test@example.com");

        // Assert
        assertTrue(result);
    }

    @Test
    public void testLoadUserByUsername() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(List.of(new Role("ROLE_USER")));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userServiceImpl.loadUserByUsername("test@example.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername("test@example.com"));
    }
}