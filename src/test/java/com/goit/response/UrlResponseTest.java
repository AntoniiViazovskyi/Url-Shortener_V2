package com.goit.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class UrlResponseTest {

    @Test
    void testNoArgsConstructor() {
        UrlResponse urlResponse = new UrlResponse();
        Assertions.assertNotNull(urlResponse);
    }

    @Test
    void testAllArgsConstructor() {
        String shortId = "abc123";
        String shortUrl = "http://short.url";
        String longUrl = "https://example.com";
        LocalDateTime expiryDate = LocalDateTime.now();

        UrlResponse urlResponse = new UrlResponse(shortId, shortUrl, longUrl, expiryDate);

        Assertions.assertEquals(shortId, urlResponse.getShortId());
        Assertions.assertEquals(shortUrl, urlResponse.getShortUrl());
        Assertions.assertEquals(longUrl, urlResponse.getLongUrl());
        Assertions.assertEquals(expiryDate, urlResponse.getExpiryDate());
    }

    @Test
    void testSetterAndGetters() {
        UrlResponse urlResponse = new UrlResponse();

        String shortId = "abc123";
        String shortUrl = "http://short.url";
        String longUrl = "https://example.com";
        LocalDateTime expiryDate = LocalDateTime.now();

        urlResponse.setShortId(shortId);
        urlResponse.setShortUrl(shortUrl);
        urlResponse.setLongUrl(longUrl);
        urlResponse.setExpiryDate(expiryDate);

        Assertions.assertEquals(shortId, urlResponse.getShortId());
        Assertions.assertEquals(shortUrl, urlResponse.getShortUrl());
        Assertions.assertEquals(longUrl, urlResponse.getLongUrl());
        Assertions.assertEquals(expiryDate, urlResponse.getExpiryDate());
    }
}
