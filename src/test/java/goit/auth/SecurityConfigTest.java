package com.goit.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.mockito.Mockito.*;

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