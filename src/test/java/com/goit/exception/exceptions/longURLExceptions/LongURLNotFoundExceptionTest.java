package com.goit.exception.exceptions.longURLExceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongURLNotFoundExceptionTest {

    @Test
    public void testConstructor() {
        LongURLNotFoundException exception = new LongURLNotFoundException();

        String expectedMessage = "Can not found long url without id.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorWithId() {
        Long id = 123L;
        LongURLNotFoundException exception = new LongURLNotFoundException(id);

        String expectedMessage = "Long url with id = 123 not found.";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
