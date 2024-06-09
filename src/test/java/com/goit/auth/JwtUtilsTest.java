package com.goit.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class JwtUtilsTest {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private String lifetime;
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void testGenerateToken() {
        String result = jwtUtils.generateToken(new User(Long.valueOf(1), "email", "password", List.of(new Role(0, "name"))));
        assertNotNull(result);
    }

    @Test
    void testGetEmail() {
        String token = jwtUtils.generateToken
                (new User(Long.valueOf(1), "test@example.com", "password", List.of(new Role(0, "name"))));
        String result = jwtUtils.getEmail(token);
        Assertions.assertEquals("test@example.com", result);
    }

    @Test
    void testGetRoles() {
        String token = jwtUtils.generateToken
                (new User(Long.valueOf(1), "test@example.com", "password", List.of(new Role(0, "name"))));
        List<String> result = jwtUtils.getRoles(token);
        List<String> expectedRoles = List.of("name");
        Assertions.assertEquals(expectedRoles, result);
    }

    @Test
    void testGetUserId() {
        String token = jwtUtils.generateToken
                (new User(1L, "test@example.com", "password", List.of(new Role(0, "name"))));
        Long result = jwtUtils.getUserId(token);
        Assertions.assertEquals(1L, result); ///
    }
}