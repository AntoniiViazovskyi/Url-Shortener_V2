package com.goit.url;

import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlCrudServiceImplTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlMapper urlMapper;

    @Mock
    private ShortUrlGenerator shortUrlGenerator;

    @InjectMocks
    private UrlCrudServiceImpl urlCrudService;

    @Test
    void testCreateShortUrl() {
        User user = new User();
        user.setUsername("testUser");

        DtoCreateUrlRequest request = DtoCreateUrlRequest.builder()
                .longUrl("https://example.com")
                .expiryDate(LocalDateTime.now().plusDays(1))
                .build();

        Url url = Url.builder()
                .longUrl(request.getLongUrl())
                .expiryDate(request.getExpiryDate())
                .user(user)
                .shortUrl("abcdefg")
                .creationDate(LocalDateTime.now())
                .clickCount(0L)
                .build();

        when(URLValidator.isValid(request.getLongUrl())).thenReturn(true);
        when(URLValidator.isAccessibleUrl(request.getLongUrl())).thenReturn(true);
        when(shortUrlGenerator.generateShortUrl()).thenReturn("abcdefg");
        when(urlMapper.toUrl(request, user)).thenReturn(url);
        when(urlRepository.save(any(Url.class))).thenReturn(url);

        Url createdUrl = urlCrudService.createShortUrl(request, user);

        assertNotNull(createdUrl, "The created URL should not be null");
        assertEquals("abcdefg", createdUrl.getShortUrl(), "The short URL should match the generated value");
        assertEquals(request.getLongUrl(), createdUrl.getLongUrl(), "The long URL should match the request value");
        assertEquals(user, createdUrl.getUser(), "The user should match the provided user");
    }


    @Test
    void testGetShortUrl() {
        Url url = new Url();
        url.setShortUrl("abcdefg");

        when(urlRepository.findByShortUrl("abcdefg")).thenReturn(Optional.of(url));

        Optional<Url> foundUrl = urlCrudService.getShortUrl("abcdefg");

        assertTrue(foundUrl.isPresent(), "The URL should be found");
        assertEquals("abcdefg", foundUrl.get().getShortUrl(), "The short URL should match the stored value");
    }

    @Test
    void testIncrementClickCount() {
        Url url = new Url();
        url.setShortUrl("abcdefg");
        url.setClickCount(0L);

        when(urlRepository.findByShortUrl("abcdefg")).thenReturn(Optional.of(url));

        urlCrudService.incrementClickCount("abcdefg");

        assertEquals(1, url.getClickCount(), "The click count should be incremented");
        verify(urlRepository, times(1)).save(url);
    }

    @Test
    void testIncrementClickCountNotFound() {
        when(urlRepository.findByShortUrl("abcdefg")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            urlCrudService.incrementClickCount("abcdefg");
        }, "An exception should be thrown if the short URL is not found");
    }
}

