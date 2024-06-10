package com.goit.auth;

import com.goit.exception.exceptions.userExceptions.UserNotFoundException;
import com.goit.url.V2.Url;
import com.goit.url.V2.UrlRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

  /*  @Test
    void testCreateUser() throws UserAlreadyExistException {
        String email = "test@example.com";
        String rawPassword = "password";
        User user = new User(email, rawPassword);
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn("encodedPassword");
        when(roleService.findByName("ROLE_USER")).thenReturn(new Role());

        UserDto savedUserDto = userService.createUser(email, rawPassword);

        assertThat(savedUserDto.getEmail()).isEqualTo(email);
        assertThat(savedUserDto.getPassword()).isEqualTo("encodedPassword");
        verify(userRepository, times(1)).save(any(User.class));
    }*/

    @Test
    void testGetUserById() {
        Long id = 1L;
        User user = new User(id, "test@example.com", "password", null, null);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDto retrievedUserDto = userService.getUserById(id);

        assertThat(retrievedUserDto.getId()).isEqualTo(id);
        assertThat(retrievedUserDto.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testGetByEmail() throws UserNotFoundException {
        String email = "test@example.com";
        User user = new User(email, "password");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User foundUser = userService.getByEmail(email);

        assertThat(foundUser.getEmail()).isEqualTo(email);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(
                new User("test1@example.com", "password1"),
                new User("test2@example.com", "password2"),
                new User("test3@example.com", "password3")
        );
        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> allUsers = userService.getAllUsers();

        assertThat(allUsers).hasSize(3);
        assertThat(allUsers.stream().map(UserDto::getEmail).collect(Collectors.toList()))
                .containsExactly("test1@example.com", "test2@example.com", "test3@example.com");
    }

    @Test
    void testGetUserWithActiveUrls() throws UserNotFoundException {
        // Arrange
        String email = "test@example.com";
        LocalDateTime now = LocalDateTime.now();
        User user = new User(email, "password");
        List<Url> activeUrls = List.of(
                new Url(null, "short1", "http://example.com/1", now, now.plusDays(1), 0, user),
                new Url(null, "short2", "http://example.com/2", now, now.plusDays(1), 0, user)
        );

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(urlRepository.findActiveUrlsByUserId(eq(user), any(LocalDateTime.class))).thenReturn(activeUrls);

        User userWithActiveUrls = userService.getUserWithActiveUrls(email);

        assertThat(userWithActiveUrls.getUrls()).hasSize(2);
    }

    @Test
    void testGetUserWithAllUrls() throws UserNotFoundException {
        String email = "test@example.com";
        User user = new User(email, "password");
        List<Url> urls = List.of(
                new Url("short1", "http://example.com/1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), 0, user),
                new Url("short2", "http://example.com/2",LocalDateTime.now(), LocalDateTime.now().plusDays(1), 0, user)
        );
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(urlRepository.findAllByUser(user)).thenReturn(urls);

        User userWithAllUrls = userService.getUserWithAllUrls(email);

        assertThat(userWithAllUrls.getUrls()).hasSize(2);
    }

    @Test
    void testUpdateUser() {
        Long id = 1L;
        UserDto userDto = new UserDto(id, "newemail@example.com");
        User user = new User(id, "oldemail@example.com", "password", null, null);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDto updatedUserDto = userService.updateUser(id, userDto);

        assertThat(updatedUserDto.getEmail()).isEqualTo("newemail@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);

        boolean deleted = userService.deleteUser(id);

        assertThat(deleted).isTrue();
        verify(userRepository, times(1)).deleteById(id);
    }
}