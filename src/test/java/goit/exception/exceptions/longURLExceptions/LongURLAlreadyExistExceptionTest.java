package com.goit.exception.exceptions.longURLExceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongURLAlreadyExistExceptionTest {

    @Test
    public void testConstructorWithId() {
        Long id = 123L;
        LongURLAlreadyExistException exception = new LongURLAlreadyExistException(id);

        String expectedMessage = "Long URL with id = 123 already exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorWithURL() {
        String url = "http://example.com";
        LongURLAlreadyExistException exception = new LongURLAlreadyExistException(url);

        String expectedMessage = "Long URL with itself url = http://example.com already exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
