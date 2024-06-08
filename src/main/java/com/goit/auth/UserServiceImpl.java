package com.goit.auth;

import com.goit.exception.LogEnum;
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

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserDto createUser(UserDto userDTO, String rawPassword) {
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(List.of(roleService.findByName("ROLE_USER")));
        User savedUser = userRepository.save(user);

        log.info(String.format("%s: User %s was created by UserDTO and rawPassword", LogEnum.SERVICE, user));
        return UserMapper.toDTO(savedUser);
    }

    public UserDto createUser(String email, String rawPassword) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(List.of(roleService.findByName("ROLE_USER")));
        User savedUser = userRepository.save(user);

        log.info(String.format("%s: User %s was created by email and rawPassword", LogEnum.SERVICE, user));
        return UserMapper.toDTO(savedUser);
    }

    // Read
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        log.info(String.format("%s request on retrieving user by id %s was sent", LogEnum.SERVICE, id));
        return user.map(UserMapper::toDTO).orElse(null);
    }

    public User getByEmail(String email) {
        log.info(String.format("%s request on retrieving user by email %s was sent", LogEnum.SERVICE, email));
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));
    }

    public List<UserDto> getAllUsers() {
        log.info(String.format("%s request on retrieving all users was sent", LogEnum.SERVICE));
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public User getUserWithActiveUrls(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));
        List<Url> activeUrls = urlRepository.findAllActiveByUserId(user.getId());
        user.setUrls(activeUrls);

        log.info(String.format("%s request on retrieving user (%s) only with active urls was sent", LogEnum.SERVICE, user));
        return user;
    }

    public User getUserWithAllUrls(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));
        List<Url> urls = urlRepository.findAllByUserId(user.getId());
        user.setUrls(urls);

        log.info(String.format("%s request on retrieving user (%s) with all urls was sent", LogEnum.SERVICE, user));
        return user;
    }

    // Update
    public UserDto updateUser(Long id, UserDto userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(userDTO.getEmail());
            User updatedUser = userRepository.save(user);

            log.info(String.format("%s user %s was updated to %s", LogEnum.SERVICE, user, updatedUser));
            return UserMapper.toDTO(updatedUser);
        }

        log.info(String.format("%s user with id %s wasn't found --> wasn't updated", LogEnum.SERVICE, id));
        return null;
    }

    // Delete
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);

            log.info(String.format("%s user with id %s was deleted", LogEnum.SERVICE, id));
            return true;
        }

        log.info(String.format("%s user with id %s doesn't exist --> wasn't deleted", LogEnum.SERVICE, id));
        return false;
    }

    // Helper methods
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }
}
