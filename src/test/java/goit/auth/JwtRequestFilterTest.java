package com.goit.auth;

import com.goit.exception.exceptions.generalExceptions.BadJWTException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
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
    void testDoFilterInternal() throws BadJWTException, ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        when(jwtUtils.getEmail(anyString())).thenReturn("getEmailResponse");
        when(jwtUtils.getRoles(anyString())).thenReturn(List.of("getRolesResponse"));
        when(requiredProperties.stream()).thenReturn(null);
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
    void testAfterPropertiesSet() throws ServletException {
        jwtRequestFilter.afterPropertiesSet();
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