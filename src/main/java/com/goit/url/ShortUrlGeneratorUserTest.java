package com.goit.url;

import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ShortUrlGeneratorUserTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private ShortUrlGeneratorUser shortUrlGeneratorUser;

    @Test
    void testGenerateShortUrl_WithExistingMaxId() {
        MockitoAnnotations.openMocks(this);

        User user = new User();
        user.setId(1L);
        user.setName("testuser");

        when(urlRepository.getMaxId()).thenReturn(Optional.of(10L));

        String shortUrl = shortUrlGeneratorUser.generateShortUrl(user);

        assertNotNull(shortUrl, "Generated short URL should not be null");
        assertTrue(shortUrl.matches("^[0-9a-f]+$"), "Generated short URL should be a hexadecimal string");
    }

    @Test
    void testGenerateShortUrl_WithNoExistingMaxId() {
        MockitoAnnotations.openMocks(this);

        User user = new User();
        user.setId(1L);
        user.setName("testuser");

        when(urlRepository.getMaxId()).thenReturn(Optional.empty());

        String shortUrl = shortUrlGeneratorUser.generateShortUrl(user);

        assertNotNull(shortUrl, "Generated short URL should not be null");
        assertTrue(shortUrl.matches("^[0-9a-f]+$"), "Generated short URL should be a hexadecimal string");
    }
}