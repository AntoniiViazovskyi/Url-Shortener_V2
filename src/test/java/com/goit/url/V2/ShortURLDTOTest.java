package com.goit.url.V2;

import com.goit.auth.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ShortURLDTOTest {

    private User creator;
    private List<User> users;

    @BeforeEach
    public void setUp() {
        creator = new User();
        creator.setId(1L);
        creator.setEmail("creator@example.com");

        users = new ArrayList<>();
        User user1 = new User();
        user1.setId(2L);
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(3L);
        user2.setEmail("user2@example.com");

        users.add(user1);
        users.add(user2);
    }

    @Test
    public void testValidShortURLDTO() {
        Long id = 1L;
        String url = "shorturl";
        String longUrl = "https://example.com";
        String shortUrl = "https://short.url/abc123";
        LocalDate expiryDate = LocalDate.now().plusDays(7);
        Long clickCount = 0L;

        ShortURLDTO shortURLDTO = ShortURLDTO.builder()
                .id(id)
                .url(url)
                .longURL(longUrl)
                .shortURL(shortUrl)
                .creator(creator)
                .users(users)
                .expiryDate(expiryDate)
                .clickCount(clickCount)
                .build();

        assertThat(shortURLDTO.getId()).isEqualTo(id);
        assertThat(shortURLDTO.getUrl()).isEqualTo(url);
        assertThat(shortURLDTO.getLongURL()).isEqualTo(longUrl);
        assertThat(shortURLDTO.getShortURL()).isEqualTo(shortUrl);
        assertThat(shortURLDTO.getCreator()).isEqualTo(creator);
        assertThat(shortURLDTO.getUsers()).isEqualTo(users);
        assertThat(shortURLDTO.getExpiryDate()).isEqualTo(expiryDate);
        assertThat(shortURLDTO.getClickCount()).isEqualTo(clickCount);
    }

    @Test
    public void testShortURLDTOSettersAndGetters() {
        ShortURLDTO shortURLDTO = new ShortURLDTO();
        Long id = 1L;
        String url = "shorturl";
        String longUrl = "https://example.com";
        String shortUrl = "https://short.url/abc123";
        LocalDate expiryDate = LocalDate.now().plusDays(7);
        Long clickCount = 0L;

        shortURLDTO.setId(id);
        shortURLDTO.setUrl(url);
        shortURLDTO.setLongURL(longUrl);
        shortURLDTO.setShortURL(shortUrl);
        shortURLDTO.setCreator(creator);
        shortURLDTO.setUsers(users);
        shortURLDTO.setExpiryDate(expiryDate);
        shortURLDTO.setClickCount(clickCount);

        assertEquals(id, shortURLDTO.getId());
        assertEquals(url, shortURLDTO.getUrl());
        assertEquals(longUrl, shortURLDTO.getLongURL());
        assertEquals(shortUrl, shortURLDTO.getShortURL());
        assertEquals(creator, shortURLDTO.getCreator());
        assertEquals(users, shortURLDTO.getUsers());
        assertEquals(expiryDate, shortURLDTO.getExpiryDate());
        assertEquals(clickCount, shortURLDTO.getClickCount());
    }

    @Test
    public void testShortURLDTOEqualsAndHashCode() {
        Long id = 1L;
        String url = "shorturl";
        String longUrl = "https://example.com";
        String shortUrl = "https://short.url/abc123";
        LocalDate expiryDate = LocalDate.now().plusDays(7);
        Long clickCount = 0L;

        ShortURLDTO shortURLDTO1 = ShortURLDTO.builder()
                .id(id)
                .url(url)
                .longURL(longUrl)
                .shortURL(shortUrl)
                .creator(creator)
                .users(users)
                .expiryDate(expiryDate)
                .clickCount(clickCount)
                .build();

        ShortURLDTO shortURLDTO2 = ShortURLDTO.builder()
                .id(id)
                .url(url)
                .longURL(longUrl)
                .shortURL(shortUrl)
                .creator(creator)
                .users(users)
                .expiryDate(expiryDate)
                .clickCount(clickCount)
                .build();

        assertEquals(shortURLDTO1, shortURLDTO2);
        assertEquals(shortURLDTO1.hashCode(), shortURLDTO2.hashCode());
    }

    @Test
    public void testShortURLDTOToString() {
        Long id = 1L;
        String url = "shorturl";
        String longUrl = "https://example.com";
        String shortUrl = "https://short.url/abc123";
        LocalDate expiryDate = LocalDate.now().plusDays(7);
        Long clickCount = 0L;

        ShortURLDTO shortURLDTO = ShortURLDTO.builder()
                .id(id)
                .url(url)
                .longURL(longUrl)
                .shortURL(shortUrl)
                .creator(creator)
                .users(users)
                .expiryDate(expiryDate)
                .clickCount(clickCount)
                .build();

        String expectedString = "ShortURLDTO(id=1, url=shorturl, longURL=https://example.com, shortURL=https://short.url/abc123, creator=User(id=1, email=creator@example.com), users=[User(id=2, email=user1@example.com), User(id=3, email=user2@example.com)], expiryDate=" + expiryDate.toString() + ", clickCount=0)";
        String actualString = shortURLDTO.toString();

        String filteredActualString = actualString.replaceAll(", password=null, roles=null", "");

        assertEquals(expectedString, filteredActualString);
    }
}