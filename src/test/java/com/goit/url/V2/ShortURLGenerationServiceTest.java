package com.goit.url.V2;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.goit.auth.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ShortURLGenerationServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private ShortURLGenerationService shortURLGenerationService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(123L);
    }

    @Test
    public void testGenerateShortURLWhenMaxIdIsPresent() {
        when(urlRepository.getMaxId()).thenReturn(Optional.of(100L));

        String shortUrl = shortURLGenerationService.generateShortURL(user);

        assertNotNull(shortUrl);
        assertEquals("7b65", shortUrl);
    }

    @Test
    public void testGenerateShortURLWhenMaxIdIsNotPresent() {
        when(urlRepository.getMaxId()).thenReturn(Optional.empty());

        String shortUrl = shortURLGenerationService.generateShortURL(user);

        assertNotNull(shortUrl);
        assertEquals("7b1", shortUrl);
    }


    @Test
    public void testGenerateShortURLInteractionWithRepository() {
        when(urlRepository.getMaxId()).thenReturn(Optional.of(100L));

        shortURLGenerationService.generateShortURL(user);

        verify(urlRepository, times(1)).getMaxId();
    }
}