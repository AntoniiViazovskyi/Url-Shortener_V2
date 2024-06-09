package com.goit.exception.exceptions.longURLExceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotValidLongURLExceptionTest {

    @Test
    public void testConstructor() {
        String longURL = "invalidurl";
        NotValidLongURLException exception = new NotValidLongURLException(longURL);

        String expectedMessage = "Url invalidurl isn't valid\n" +
                "Please check if it is real url";
        assertEquals(expectedMessage.trim(), exception.getMessage().trim());
    }

    @Test
    public void testConstructorWithDifferentURL() {
        String longURL = "http://example.com";
        NotValidLongURLException exception = new NotValidLongURLException(longURL);

        String expectedMessage = "Url http://example.com isn't valid\n" +
                "Please check if it is real url";
        assertEquals(expectedMessage.trim(), exception.getMessage().trim());
    }
}
