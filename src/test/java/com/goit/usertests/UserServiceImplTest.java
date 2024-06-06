package com.goit.usertests;

import com.goit.user.User;
import com.goit.user.UserDto;
import com.goit.user.UserRepository;
import com.goit.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setPassword("password");

        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");

        // Act
        UserDto createdUser = userService.createUser(userDto, "password");

        // Assert
        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        assertEquals("encodedPassword", createdUser.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testGetUserById() {
        // Arrange
        Long userId = 1L;
        User user = new User(userId, "testuser", "encodedPassword");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserDto foundUser = userService.getUserById(userId);

        // Assert
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        assertEquals("encodedPassword", foundUser.getPassword());
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> userList = Arrays.asList(
                new User(1L, "user1", "password1"),
                new User(2L, "user2", "password2")
        );
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserDto> users = userService.getAllUsers();

        // Assert
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserByUsername() {
        // Arrange
        String username = "testuser";
        User user = new User(1L, username, "encodedPassword");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        UserDto foundUser = userService.getUserByUsername(username);

        // Assert
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    void testUpdateUser() {
        // Arrange
        Long userId = 1L;
        UserDto updatedUserDto = new UserDto(userId, "updatedUser", "password");
        User existingUser = new User(userId, "oldUsername", "encodedPassword");
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Act
        UserDto updatedUser = userService.updateUser(userId, updatedUserDto);

        // Assert
        assertNotNull(updatedUser);
        assertEquals("updatedUser", updatedUser.getUsername());
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        boolean isDeleted = userService.deleteUser(userId);

        // Assert
        assertTrue(isDeleted);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void testExistsByUsername() {
        // Arrange
        String username = "testuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        // Act
        boolean exists = userService.existsByUsername(username);

        // Assert
        assertTrue(exists);
    }
}