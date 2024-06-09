package com.goit.url.V2;

import com.goit.auth.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShortURLMapperTest {

    private final ShortURLMapper shortURLMapper = new ShortURLMapper();

    @Test
    public void testToDTO() {
        ShortURL shortURL = new ShortURL();
        shortURL.setUrl("shorturl");
        shortURL.setLongURL("https://example.com");
        shortURL.setCreator(new User("creator@example.com", "password1"));
        shortURL.setUsers(Arrays.asList(new User("user1@example.com", "password2"), new User("user2@example.com", "password3")));
        shortURL.setExpiryDate(LocalDate.of(2024, 6, 16));
        shortURL.setClickCount(10L);

        ShortURLDTO shortURLDTO = shortURLMapper.toDTO(shortURL);

        assertNotNull(shortURLDTO);
        assertNull(shortURLDTO.getId());
        assertEquals(shortURL.getUrl(), shortURLDTO.getUrl());
        assertEquals(shortURL.getLongURL(), shortURLDTO.getLongURL());
        assertEquals(shortURL.getCreator().getEmail(), shortURLDTO.getCreator().getEmail());
        assertEquals(shortURL.getCreator().getPassword(), shortURLDTO.getCreator().getPassword());
        assertEquals(shortURL.getUsers().get(0).getEmail(), shortURLDTO.getUsers().get(0).getEmail());
        assertEquals(shortURL.getUsers().get(0).getPassword(), shortURLDTO.getUsers().get(0).getPassword());
        assertEquals(shortURL.getUsers().get(1).getEmail(), shortURLDTO.getUsers().get(1).getEmail());
        assertEquals(shortURL.getUsers().get(1).getPassword(), shortURLDTO.getUsers().get(1).getPassword());
        assertEquals(shortURL.getExpiryDate(), shortURLDTO.getExpiryDate());
        assertEquals(shortURL.getClickCount(), shortURLDTO.getClickCount());
    }

    @Test
    public void testToEntity() {
        User creator = new User("creator@example.com", "creator_password");
        User user1 = new User("user1@example.com", "user1_password");
        User user2 = new User("user2@example.com", "user2_password");

        List<User> users = Arrays.asList(user1, user2);

        ShortURLDTO shortURLDTO = new ShortURLDTO();
        shortURLDTO.setId(1L);
        shortURLDTO.setUrl("shorturl");
        shortURLDTO.setLongURL("https://example.com");
        shortURLDTO.setCreator(creator);
        shortURLDTO.setUsers(users);
        shortURLDTO.setExpiryDate(LocalDate.of(2024, 6, 16));
        shortURLDTO.setClickCount(10L);

        ShortURL shortURL = shortURLMapper.toEntity(shortURLDTO);

        assertNotNull(shortURL);
        assertEquals(shortURLDTO.getId(), shortURL.getId());
        assertEquals(shortURLDTO.getUrl(), shortURL.getUrl());
        assertEquals(shortURLDTO.getLongURL(), shortURL.getLongURL());
        assertEquals(shortURLDTO.getCreator().getEmail(), shortURL.getCreator().getEmail());
        assertEquals(shortURLDTO.getUsers().size(), shortURL.getUsers().size());
        assertEquals(shortURLDTO.getExpiryDate(), shortURL.getExpiryDate());
        assertEquals(shortURLDTO.getClickCount(), shortURL.getClickCount());
    }
}