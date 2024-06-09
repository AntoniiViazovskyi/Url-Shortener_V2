package com.goit.request.auth;

import com.goit.request.auth.LoginRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testValidLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        assertTrue(validator.validate(loginRequest).isEmpty());
    }

    @Test
    void testInvalidEmail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("invalid_email");
        loginRequest.setPassword("password123");

        assertFalse(validator.validate(loginRequest).isEmpty());
    }

    @Test
    void testInvalidPasswordLength() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("pa");

        assertFalse(validator.validate(loginRequest).isEmpty());
    }
}