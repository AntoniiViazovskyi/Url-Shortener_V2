package com.goit.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlStatsResponseTest {

    @Test
    void testAllArgsConstructor() {
        String shortId = "abc123";
        String shortUrl = "http://shortener.com/";
        String longUrl = "http://example.com/long-url";
        int clickCount = 10;

        UrlStatsResponse urlStatsResponse = new UrlStatsResponse(shortId, shortUrl, longUrl, clickCount);

        Assertions.assertEquals(shortId, urlStatsResponse.getShortId());
        Assertions.assertEquals(shortUrl, urlStatsResponse.getShortUrl());
        Assertions.assertEquals(longUrl, urlStatsResponse.getLongUrl());
        Assertions.assertEquals(clickCount, urlStatsResponse.getClickCount());
    }

    @Test
    void testGettersAndSetters() {
        UrlStatsResponse urlStatsResponse = new UrlStatsResponse();

        String shortId = "abc123";
        urlStatsResponse.setShortId(shortId);
        String shortUrl = "http://shortener.com/";
        urlStatsResponse.setShortUrl(shortUrl);
        String longUrl = "http://example.com/long-url";
        urlStatsResponse.setLongUrl(longUrl);
        int clickCount = 10;
        urlStatsResponse.setClickCount(clickCount);

        Assertions.assertEquals(shortId, urlStatsResponse.getShortId());
        Assertions.assertEquals(shortUrl, urlStatsResponse.getShortUrl());
        Assertions.assertEquals(shortId, urlStatsResponse.getShortId());
        Assertions.assertEquals(longUrl, urlStatsResponse.getLongUrl());
        Assertions.assertEquals(clickCount, urlStatsResponse.getClickCount());
    }
}
