package com.goit.exception.exceptions.shortURLExceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortURLAlreadyExistExceptionTest {

    @Test
    public void testConstructorWithId() {
        Long id = 123L;
        ShortURLAlreadyExistException exception = new ShortURLAlreadyExistException(id);

        String expectedMessage = "Short URL with id = 123 already exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorWithURL() {
        String url = "http://short.url";
        ShortURLAlreadyExistException exception = new ShortURLAlreadyExistException(url);

        String expectedMessage = "Short URL with itself url = http://short.url already exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
