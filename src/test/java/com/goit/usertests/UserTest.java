package com.goit.usertests;


import com.goit.user.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testValidUser() {
        User user = new User("testuser", "Password1");
        assertTrue(validator.validate(user).isEmpty());
        assertNotNull(user.getPassword());
        assertTrue(user.getPassword().length() > 0);
    }

    @Test
    public void testInvalidUsername() {
        User user = new User("a", "Password1");
        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void testInvalidPassword() {
        User user = new User("testuser", "password");
        assertFalse(validator.validate(user).isEmpty());
    }
}