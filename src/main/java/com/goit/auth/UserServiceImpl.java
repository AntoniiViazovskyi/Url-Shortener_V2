package com.goit.auth;

import com.goit.exception.LogEnum;
import com.goit.exception.exceptions.userExceptions.UserAlreadyExistException;
import com.goit.exception.exceptions.userExceptions.UserNotFoundException;
import com.goit.url.V2.Url;
import com.goit.url.V2.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final UrlRepository urlRepository;
    private final RoleService roleService;

    private PasswordEncoder passwordEncoder;
    @Autowired
    public void passwordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Create
    public UserDto createUser(String email, String rawPassword) throws UserAlreadyExistException {
        if (existsByEmail(email)) throw new UserAlreadyExistException(email);
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(List.of(roleService.findByName("ROLE_USER")));
        User savedUser = userRepository.save(user);

        log.info("{}: User (id: {}) was created by UserDTO and rawPassword", LogEnum.SERVICE, user.getId());
        return UserMapper.toDTO(savedUser);
    }

    // Read
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        log.info("{}: request on retrieving user by id {} was sent", LogEnum.SERVICE, id);
        return user.map(UserMapper::toDTO).orElse(null);
    }

    public User getByEmail(String email) throws UserNotFoundException {
        log.info("{}: request on retrieving user by email {} was sent", LogEnum.SERVICE, email);
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));
    }

    public List<UserDto> getAllUsers() {
        log.info("{}: request on retrieving all users was sent", LogEnum.SERVICE);
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public User getUserWithActiveUrls(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));
        List<Url> activeUrls = urlRepository.findActiveUrlsByUserId(user, LocalDateTime.now());
        user.setUrls(activeUrls);

        log.info("{}: request on retrieving user (id: {}) only with active urls was sent", LogEnum.SERVICE, user.getId());
        return user;
    }

    public User getUserWithAllUrls(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));
        List<Url> urls = urlRepository.findAllByUser(user);
        user.setUrls(urls);

        log.info("{}: request on retrieving user (id: {}) with all urls was sent", LogEnum.SERVICE, user.getId());
        return user;
    }

    // Update
    public UserDto updateUser(Long id, UserDto userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(userDTO.getEmail());
            User updatedUser = userRepository.save(user);

            log.info("{}: user {} was updated to {}", LogEnum.SERVICE, user, updatedUser);
            return UserMapper.toDTO(updatedUser);
        }

        log.info("{}: user with id {} wasn't found --> wasn't updated", LogEnum.SERVICE, id);
        return null;
    }

    // Delete
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);

            log.info("{}: user with id {} was deleted", LogEnum.SERVICE, id);
            return true;
        }

        log.info("{}: user with id {} doesn't exist --> wasn't deleted", LogEnum.SERVICE, id);
        return false;
    }

    // Helper methods
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = null;
        try {
            user = getByEmail(email);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }
}
