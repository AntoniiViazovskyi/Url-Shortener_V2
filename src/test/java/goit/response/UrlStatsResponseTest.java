package com.goit.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlStatsResponseTest {

    @Test
    void testAllArgsConstructor() {
        String shortId = "abc123";
        String longUrl = "http://example.com/long-url";
        int clickCount = 10;

        UrlStatsResponse urlStatsResponse = new UrlStatsResponse(shortId, longUrl, clickCount);

        Assertions.assertEquals(shortId, urlStatsResponse.getShortId());
        Assertions.assertEquals(longUrl, urlStatsResponse.getLongUrl());
        Assertions.assertEquals(clickCount, urlStatsResponse.getClickCount());
    }

    @Test
    void testGettersAndSetters() {
        UrlStatsResponse urlStatsResponse = new UrlStatsResponse();

        String shortId = "abc123";
        urlStatsResponse.setShortId(shortId);
        String longUrl = "http://example.com/long-url";
        urlStatsResponse.setLongUrl(longUrl);
        int clickCount = 10;
        urlStatsResponse.setClickCount(clickCount);

        Assertions.assertEquals(shortId, urlStatsResponse.getShortId());
        Assertions.assertEquals(longUrl, urlStatsResponse.getLongUrl());
        Assertions.assertEquals(clickCount, urlStatsResponse.getClickCount());
    }
}
