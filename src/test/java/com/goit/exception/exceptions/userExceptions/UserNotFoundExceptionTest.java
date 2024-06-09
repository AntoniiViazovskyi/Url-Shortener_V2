package com.goit.exception.exceptions.userExceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserNotFoundExceptionTest {

    @Test
    public void testConstructorWithUsername() {
        String username = "testUser";
        UserNotFoundException exception = new UserNotFoundException(username);

        String expectedMessage = "User with username = testUser not found.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorWithId() {
        Long id = 123L;
        UserNotFoundException exception = new UserNotFoundException(id);

        String expectedMessage = "User with id = 123 not found.";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
