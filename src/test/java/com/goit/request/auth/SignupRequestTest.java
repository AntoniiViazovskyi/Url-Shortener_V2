package com.goit.request.auth;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testValidSignupRequest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword("password123");

        assertTrue(validator.validate(signupRequest).isEmpty());
    }

    @Test
    void testInvalidEmail() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("invalid_email");
        signupRequest.setPassword("password123");

        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    @Test
    void testInvalidPasswordLength() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword("pw");

        assertFalse(validator.validate(signupRequest).isEmpty());
    }
}