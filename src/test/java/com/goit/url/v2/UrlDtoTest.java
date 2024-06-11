package com.goit.url.v2;

import com.goit.auth.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UrlDtoTest {

    @Test
    public void testConstructorAndGetters() {
        Long id = 1L;
        User user = new User();
        String shortId = "abc123";
        String longURL = "http://example.com";
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime expiryDate = creationDate.plusDays(30);
        int clickCount = 0;

        UrlDto urlDto = new UrlDto(id, user, shortId, longURL, creationDate, expiryDate, clickCount);

        assertEquals(id, urlDto.getId());
        assertEquals(user, urlDto.getUser());
        assertEquals(shortId, urlDto.getShortId());
        assertEquals(longURL, urlDto.getLongURL());
        assertEquals(creationDate, urlDto.getCreationDate());
        assertEquals(expiryDate, urlDto.getExpiryDate());
        assertEquals(clickCount, urlDto.getClickCount());
    }

    @Test
    public void testNoArgsConstructor() {
        UrlDto urlDto = new UrlDto();

        assertNull(urlDto.getId());
        assertNull(urlDto.getUser());
        assertNull(urlDto.getShortId());
        assertNull(urlDto.getLongURL());
        assertNull(urlDto.getCreationDate());
        assertNull(urlDto.getExpiryDate());
        assertEquals(0, urlDto.getClickCount());
    }
}