package com.goit.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CreateShortUrlRequestTest {

    @Test
    void testNoArgsConstructor() {
        CreateShortUrlRequest request = new CreateShortUrlRequest();
        Assertions.assertNotNull(request);
    }

    @Test
    void testAllArgsConstructor() {
        String longUrl = "https://example.com";
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(30);

        CreateShortUrlRequest request = new CreateShortUrlRequest(longUrl, expiryDate);

        Assertions.assertEquals(longUrl, request.getLongUrl());
        Assertions.assertEquals(expiryDate, request.getExpiryDate());
    }

    @Test
    void testSetterAndGetters() {
        CreateShortUrlRequest request = new CreateShortUrlRequest();

        String longUrl = "https://example.com";
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(30);

        request.setLongUrl(longUrl);
        request.setExpiryDate(expiryDate);

        Assertions.assertEquals(longUrl, request.getLongUrl());
        Assertions.assertEquals(expiryDate, request.getExpiryDate());
    }
}