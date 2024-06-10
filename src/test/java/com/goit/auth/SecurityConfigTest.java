package com.goit.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

class SecurityConfigTest {
    @Mock
    UserService userService;
    @Mock
    JwtRequestFilter jwtRequestFilter;
    @InjectMocks
    SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCorsConfigurer() {
        WebMvcConfigurer result = securityConfig.corsConfigurer();
        Assertions.assertNotNull(result);
    }

    @Test
    void testPasswordEncoder() {
        BCryptPasswordEncoder result = securityConfig.passwordEncoder();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result instanceof BCryptPasswordEncoder);
    }
}