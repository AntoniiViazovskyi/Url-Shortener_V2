package com.goit.exception.exceptions.userExceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserIncorrectPasswordExceptionTest {

    @Test
    public void testConstructor() {
        String username = "testUser";
        UserIncorrectPasswordException exception = new UserIncorrectPasswordException(username);

        String expectedMessage = "Incorrect password for user with username = testUser.";
        assertEquals(expectedMessage, exception.getMessage());
    }
}