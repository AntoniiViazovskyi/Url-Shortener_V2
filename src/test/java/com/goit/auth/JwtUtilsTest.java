package com.goit.auth;

import com.goit.exception.exceptions.generalExceptions.BadJWTException;
import com.goit.url.V2.Url;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@RunWith(SpringRunner.class)
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
        String result = jwtUtils.generateToken(new User(1L, "email", "password",
                List.of(new Role("name")), List.of(new Url(1L, "shortId", "longUrl",
                LocalDateTime.of(2024, Month.JUNE, 9, 21, 35, 57), LocalDateTime.of(2024,
                Month.JUNE, 9, 21, 35, 57), 0, null))));
        assertNotNull(result);
    }

    @Test
    void testGetEmail() throws BadJWTException {
        String token = jwtUtils.generateToken(new User(1L, "email", "password",
                List.of(new Role("name")), List.of(new Url(1L, "shortId", "longUrl",
                LocalDateTime.of(2024, Month.JUNE, 9, 21, 35, 57), LocalDateTime.of(2024,
                Month.JUNE, 9, 21, 35, 57), 0, null))));
        String result = jwtUtils.getEmail(token);
        Assertions.assertEquals("email", result);
    }

    @Test
    void testGetRoles() throws BadJWTException {
        String token = jwtUtils.generateToken(new User(1L, "email", "password",
                List.of(new Role("name")), List.of(new Url(1L, "shortId", "longUrl",
                LocalDateTime.of(2024, Month.JUNE, 9, 21, 35, 57), LocalDateTime.of(2024,
                Month.JUNE, 9, 21, 35, 57), 0, null))));
        List<String> result = jwtUtils.getRoles(token);
        List<String> expectedRoles = List.of("name");
        Assertions.assertEquals(expectedRoles, result);
    }
}