package com.goit.url.V2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class URLValidatorTest {

    @Test
    void testIsValid_ValidUrl_ReturnsTrue() {
        boolean result = URLValidator.isValid("https://example.com");
        Assertions.assertTrue(result);
    }

    @Test
    void testIsValid_InvalidUrl_ReturnsFalse() {
        boolean result = URLValidator.isValid("not_a_url");
        Assertions.assertFalse(result);
    }

    @Test
    void testIsAccessibleUrl_AccessibleUrl_ReturnsTrue() {
        boolean result = URLValidator.isAccessibleUrl("https://example.com");
        Assertions.assertTrue(result);
    }

    @Test
    void testIsAccessibleUrl_InaccessibleUrl_ReturnsFalse() {
        boolean result = URLValidator.isAccessibleUrl("https://nonexistenturl.com");
        Assertions.assertFalse(result);
    }

    @Test
    void testIsValid_NullUrl_ReturnsFalse() {
        boolean result = URLValidator.isValid(null);
        Assertions.assertFalse(result);
    }

    @Test
    void testIsValid_EmptyUrl_ReturnsFalse() {
        boolean result = URLValidator.isValid("");
        Assertions.assertFalse(result);
    }
}