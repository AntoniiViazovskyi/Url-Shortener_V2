package com.goit.auth;

import com.goit.exception.exceptions.userExceptions.UserAlreadyExistException;
import com.goit.exception.exceptions.userExceptions.UserNotFoundException;
import com.goit.url.V2.Url;
import com.goit.url.V2.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
        return UserMapper.toDTO(savedUser);
    }

    // Read
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper::toDTO).orElse(null);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public User getByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));
    }

    public User getUserWithActiveUrls(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));
        List<Url> activeUrls = urlRepository.findActiveUrlsByUserId(user, LocalDateTime.now());
        user.setUrls(activeUrls);
        return user;
    }

    public User getUserWithAllUrls(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));
        List<Url> urls = urlRepository.findAllByUser(user);
        user.setUrls(urls);
        return user;
    }

    // Update
    public UserDto updateUser(Long id, UserDto userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(userDTO.getEmail());
            User updatedUser = userRepository.save(user);
            return UserMapper.toDTO(updatedUser);
        }
        return null;
    }

    // Delete
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
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
