package com.goit.url;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ShortUrlGeneratorUserTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private ShortUrlGeneratorUser shortUrlGeneratorUser;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .build();
    }

    @Test
    public void testGenerateShortUrl_WithExistingMaxId() {
        when(urlRepository.getMaxId()).thenReturn(Optional.of(10L));

        String shortUrl = shortUrlGeneratorUser.generateShortUrl(user);

        assertNotNull(shortUrl, "Generated short URL should not be null");
        assertEquals("1b", shortUrl, "Generated short URL should be '1b'");
    }

    @Test
    public void testGenerateShortUrl_WithNoExistingMaxId() {
        when(urlRepository.getMaxId()).thenReturn(Optional.empty());

        String shortUrl = shortUrlGeneratorUser.generateShortUrl(user);

        assertNotNull(shortUrl, "Generated short URL should not be null");
        assertEquals("11", shortUrl, "Generated short URL should be '11'");
    }
}

