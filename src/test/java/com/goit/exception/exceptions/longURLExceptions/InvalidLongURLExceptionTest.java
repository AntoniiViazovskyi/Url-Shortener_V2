package com.goit.exception.exceptions.longURLExceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvalidLongURLExceptionTest {

    @Test
    void testInvalidLongURLException() {
        // Arrange
        String longURL = "example.com";
        InvalidLongURLException exception = new InvalidLongURLException(longURL);

        // Act
        String message = exception.getMessage();

        // Assert
        String expectedMessage = """
            Your URL is not valid or has incorrect format.
            Url must be structured like this:
            <schema>://<login>:<password>@<host>:<port>/<path>?<parameters>#<anchor>
            Your URL:
            example.com""";
        assertEquals(expectedMessage, message);
    }
}