package com.goit.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class UrlResponseTest {

    @Test
    void testNoArgsConstructor() {
        UrlResponse urlResponse = new UrlResponse();
        Assertions.assertNotNull(urlResponse);
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        Long userId = 2L;
        String shortId = "abc123";
        String longUrl = "https://example.com";
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime expiryDate = creationDate.plusDays(30);
        int clickCount = 10;

        UrlResponse urlResponse = new UrlResponse(id, userId, shortId, longUrl, creationDate, expiryDate, clickCount);

        Assertions.assertEquals(id, urlResponse.getId());
        Assertions.assertEquals(userId, urlResponse.getUserId());
        Assertions.assertEquals(shortId, urlResponse.getShortId());
        Assertions.assertEquals(longUrl, urlResponse.getLongUrl());
        Assertions.assertEquals(creationDate, urlResponse.getCreationDate());
        Assertions.assertEquals(expiryDate, urlResponse.getExpiryDate());
        Assertions.assertEquals(clickCount, urlResponse.getClickCount());
    }

    @Test
    void testSetterAndGetters() {
        UrlResponse urlResponse = new UrlResponse();

        Long id = 1L;
        Long userId = 2L;
        String shortId = "abc123";
        String longUrl = "https://example.com";
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime expiryDate = creationDate.plusDays(30);
        int clickCount = 10;

        urlResponse.setId(id);
        urlResponse.setUserId(userId);
        urlResponse.setShortId(shortId);
        urlResponse.setLongUrl(longUrl);
        urlResponse.setCreationDate(creationDate);
        urlResponse.setExpiryDate(expiryDate);
        urlResponse.setClickCount(clickCount);

        Assertions.assertEquals(id, urlResponse.getId());
        Assertions.assertEquals(userId, urlResponse.getUserId());
        Assertions.assertEquals(shortId, urlResponse.getShortId());
        Assertions.assertEquals(longUrl, urlResponse.getLongUrl());
        Assertions.assertEquals(creationDate, urlResponse.getCreationDate());
        Assertions.assertEquals(expiryDate, urlResponse.getExpiryDate());
        Assertions.assertEquals(clickCount, urlResponse.getClickCount());
    }
}
