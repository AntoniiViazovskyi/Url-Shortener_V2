package com.goit.exception.exceptions.shortURLExceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortURLRedirectionExceptionTest {

    @Test
    public void testConstructor() {
        ShortURLRedirectionException exception = new ShortURLRedirectionException();

        String expectedMessage = "Cannot redirect. Check the validity of the data";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorWithShortURL() {
        String shortURL = "http://example.com/short";
        ShortURLRedirectionException exception = new ShortURLRedirectionException(shortURL);

        String expectedMessage = "There is no way to perform a redirect to http://example.com/short.\n" +
                "Please check the expiration date of the short url";
        assertEquals(expectedMessage.trim(), exception.getMessage().trim());
    }
}
