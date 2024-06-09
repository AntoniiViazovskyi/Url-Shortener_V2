package com.goit.exception.exceptions.shortURLExceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortURLNotFoundExceptionTest {

    @Test
    public void testConstructor() {
        ShortURLNotFoundException exception = new ShortURLNotFoundException();

        String expectedMessage = "Can not found short url without id.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorWithId() {
        Long id = 123L;
        ShortURLNotFoundException exception = new ShortURLNotFoundException(id);

        String expectedMessage = "Short url with id = 123 not found.";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
