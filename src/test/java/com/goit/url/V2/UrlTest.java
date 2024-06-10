package com.goit.url.V2;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import com.goit.auth.User;
import org.junit.jupiter.api.Test;

public class UrlTest {

    @Test
    public void testUrlConstructorAndGetters() {
        Long id = 1L;
        String shortUrl = "shorturl";
        String longUrl = "https://example.com";
        LocalDateTime creationDate = LocalDateTime.of(2024, 6, 9, 12, 0);
        LocalDateTime expiryDate = LocalDateTime.of(2024, 12, 31, 23, 59);
        int clickCount = 0;
        User user = new User();

        Url url = new Url(id, shortUrl, longUrl, creationDate, expiryDate, clickCount, user);

        assertThat(url.getId()).isEqualTo(id);
        assertThat(url.getLongUrl()).isEqualTo(longUrl);
        assertThat(url.getCreationDate()).isEqualTo(creationDate);
        assertThat(url.getExpiryDate()).isEqualTo(expiryDate);
        assertThat(url.getClickCount()).isEqualTo(clickCount);
        assertThat(url.getUser()).isEqualTo(user);
    }

    @Test
    public void testUrlBuilder() {
        Long id = 1L;
        String longUrl = "https://example.com";
        LocalDateTime creationDate = LocalDateTime.of(2024, 6, 9, 12, 0);
        LocalDateTime expiryDate = LocalDateTime.of(2024, 12, 31, 23, 59);
        int clickCount = 0;
        User user = new User();

        Url url = Url.builder()
                .id(id)
                .longUrl(longUrl)
                .creationDate(creationDate)
                .expiryDate(expiryDate)
                .clickCount(clickCount)
                .user(user)
                .build();

        assertThat(url.getId()).isEqualTo(id);
        assertThat(url.getLongUrl()).isEqualTo(longUrl);
        assertThat(url.getCreationDate()).isEqualTo(creationDate);
        assertThat(url.getExpiryDate()).isEqualTo(expiryDate);
        assertThat(url.getClickCount()).isEqualTo(clickCount);
        assertThat(url.getUser()).isEqualTo(user);
    }
}