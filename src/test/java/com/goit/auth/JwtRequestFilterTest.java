package com.goit.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

class JwtRequestFilterTest {
    @Mock
    JwtUtils jwtUtils;
    @Mock
    Log logger;
    @Mock
    Environment environment;
    @Mock
    ServletContext servletContext;
    @Mock
    FilterConfig filterConfig;
    @Mock
    Set<String> requiredProperties;
    @InjectMocks
    JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternal() throws ServletException, IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String jwt = "valid_jwt";
        String username = "test@example.com";
        Collection<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));


        when(jwtUtils.getEmail(jwt)).thenReturn(username);
        when(jwtUtils.getRoles(jwt)).thenReturn(List.of("ROLE_USER"));
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtils).getEmail(jwt);
        verify(jwtUtils).getRoles(jwt);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null,authorities);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testSetBeanName() {
        jwtRequestFilter.setBeanName("beanName");
    }

    @Test
    void testSetEnvironment() {
        jwtRequestFilter.setEnvironment(null);
    }

    @Test
    void testSetServletContext() {
        jwtRequestFilter.setServletContext(null);
    }

    @Test
    void testDestroy() {
        jwtRequestFilter.destroy();
    }

    @Test
    void testGetFilterConfig() {
        FilterConfig result = jwtRequestFilter.getFilterConfig();
        Assertions.assertEquals(null, result);
    }
}