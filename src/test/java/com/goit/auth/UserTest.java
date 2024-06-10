package com.goit.auth;

import com.goit.url.V2.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserTest {

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        Assertions.assertNotNull(user);
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String email = "test@example.com";
        String password = "password";
        List<Role> roles = new ArrayList<>();
        List<Url> urls = new ArrayList<>();

        User user = new User(id, email, password, roles, urls);

        Assertions.assertEquals(id, user.getId());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertEquals(roles, user.getRoles());
        Assertions.assertEquals(urls, user.getUrls());
    }

    @Test
    void testSetterAndGetters() {
        User user = new User();

        Long id = 1L;
        String email = "test@example.com";
        String password = "password";
        List<Role> roles = new ArrayList<>();
        List<Url> urls = new ArrayList<>();

        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
        user.setUrls(urls);

        Assertions.assertEquals(id, user.getId());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertEquals(roles, user.getRoles());
        Assertions.assertEquals(urls, user.getUrls());
    }

    @Test
    void testConstructorWithEmailAndPassword() {
        String email = "test@example.com";
        String password = "password";

        User user = new User(email, password);

        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(password, user.getPassword());
    }
}