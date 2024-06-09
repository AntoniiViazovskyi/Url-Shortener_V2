package com.goit.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;

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
        String userId = "user123";
        String shortId = "abc123";
        String shortUrl = "http://example.com/abc123";
        String longUrl = "http://example.com/long-url";
        int clickCount = 10;
        Date createdAt = new Date(System.currentTimeMillis());
        Date expirationAt = new Date(System.currentTimeMillis());

        UrlResponse urlResponse = new UrlResponse(id, userId, shortId, shortUrl, longUrl, clickCount, createdAt, expirationAt);

        Assertions.assertEquals(id, urlResponse.getId());
        Assertions.assertEquals(userId, urlResponse.getUserId());
        Assertions.assertEquals(shortId, urlResponse.getShortId());
        Assertions.assertEquals(shortUrl, urlResponse.getShortUrl());
        Assertions.assertEquals(longUrl, urlResponse.getLongUrl());
        Assertions.assertEquals(clickCount, urlResponse.getClick_count());
        Assertions.assertEquals(createdAt, urlResponse.getCreated_at());
        Assertions.assertEquals(expirationAt, urlResponse.getExpiration_at());
    }


}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme