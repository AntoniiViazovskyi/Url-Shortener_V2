package com.goit.exception.exceptions.longURLExceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidLongURLExceptionTest {

    @Test
    public void testConstructor() {
        String longURL = "http://example.com";
        InvalidLongURLException exception = new InvalidLongURLException(longURL);

        String expectedMessage = "Your URL isn't in the correct format.\n" +
                "Url must be structured like this:\n" +
                "<schema>://<login>:<password>@<host>:<port>/<path>?<parameters>#<anchor>\n" +
                "Your URL:\n" +
                "http://example.com";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorWithDifferentURL() {
        String longURL = "https://test.org/path?param=value#anchor";
        InvalidLongURLException exception = new InvalidLongURLException(longURL);

        String expectedMessage = "Your URL isn't in the correct format.\n" +
                "Url must be structured like this:\n" +
                "<schema>://<login>:<password>@<host>:<port>/<path>?<parameters>#<anchor>\n" +
                "Your URL:\n" +
                "https://test.org/path?param=value#anchor";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
