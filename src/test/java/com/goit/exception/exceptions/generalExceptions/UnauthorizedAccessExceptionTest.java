package com.goit.exception.exceptions.generalExceptions;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnauthorizedAccessExceptionTest {

    @Test
    public void testConstructorWithoutId() {
        UnauthorizedAccessException exception = new UnauthorizedAccessException();

        assertEquals("You don't have permission to this data", exception.getMessage());
    }

    @Test
    public void testConstructorWithId() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        UnauthorizedAccessException exception = new UnauthorizedAccessException(ids);

        String expectedMessage = "You are not creator of this link. Users can edit only their own links.\n" +
                "The id's of your links: [1, 2, 3]";

        String[] expectedLines = expectedMessage.split("\\r?\\n");
        String[] actualLines = exception.getMessage().split("\\r?\\n");

        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals(expectedLines[i], actualLines[i]);
        }
    }
}