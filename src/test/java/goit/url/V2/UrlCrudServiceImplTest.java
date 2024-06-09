package com.goit.url.V2;

import com.goit.auth.Role;
import com.goit.auth.User;
import com.goit.auth.UserRepository;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class UrlCrudServiceImplTest {
    @Mock
    UrlRepository urlRepository;
    @Mock
    UrlMapper urlMapper;
    @Mock
    ShortURLGenerationService shortURLGenerationService;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UrlCrudServiceImpl urlCrudServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateURL() {
        User user = new User(1L, "email", "password", List.of(new Role("name")), List.of());
        UrlDto urlDto = new UrlDto(1L, user, "shortId", "http://example.com",
                LocalDateTime.of(2024, Month.JUNE, 9, 22, 17, 44),
                LocalDateTime.of(2024, Month.JUNE, 9, 22, 17, 44), 0);

        Url url = new Url(1L, "shortId", "http://example.com",
                LocalDateTime.of(2024, Month.JUNE, 9, 22, 17, 44),
                LocalDateTime.of(2024, Month.JUNE, 9, 22, 17, 44), 0, user);

        UrlDto expectedUrlDto = new UrlDto(1L, user, "shortId", "http://example.com",
                LocalDateTime.of(2024, Month.JUNE, 9, 22, 17, 44),
                LocalDateTime.of(2024, Month.JUNE, 9, 22, 17, 44), 0);

        when(shortURLGenerationService.generateShortURL(any(User.class))).thenReturn("shortId");
        when(urlMapper.toEntity(any(UrlDto.class))).thenReturn(url);
        when(urlRepository.save(any(Url.class))).thenReturn(url);
        when(urlMapper.toDTO(any(Url.class))).thenReturn(expectedUrlDto);

        UrlDto result = urlCrudServiceImpl.createURL(urlDto);

        Assertions.assertEquals(expectedUrlDto, result);
    }

    @Test
    void testGetURLByShortId() {
        when(urlRepository.findByShortId(anyString())).thenReturn(Optional.empty());

        Optional<UrlDto> result = urlCrudServiceImpl.getURLByShortId("shortId");

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetURLByShortIdAndUser() {
        when(urlRepository.findByShortIdAndUser(anyString(), any(User.class))).thenReturn(Optional.empty());

        Optional<UrlDto> result = urlCrudServiceImpl.getURLByShortIdAndUser
                ("shortId", new User(Long.valueOf(1), "email", "password", List.of(new Role("name")),
                        List.of(new Url(Long.valueOf(1), "shortId", "longUrl", LocalDateTime.of(2024,
                                Month.JUNE, 9, 22, 17, 44), LocalDateTime.of(2024,
                                Month.JUNE, 9, 22, 17, 44), 0, null))));

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetURLById() {
        when(urlRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<UrlDto> result = urlCrudServiceImpl.getURLById(Long.valueOf(1));

        Assertions.assertTrue(result.isEmpty());
    }


    @Test
    void testUpdateClicksCount() {
        urlCrudServiceImpl.updateClicksCount("shortId");

        verify(urlRepository).incrementClickCount(anyString());
    }

    @Test
    void testDeleteByShortIdAndUser() throws ShortURLNotFoundException {
        when(urlRepository.deleteUrlByShortIdAndUser(anyString(), any(User.class))).thenReturn(0);
        User user = new User(Long.valueOf(1), "email", "password", List.of(new Role("name")),
                List.of(new Url(Long.valueOf(1), "shortId", "longUrl",
                        LocalDateTime.of(2024, Month.JUNE, 9, 22, 17, 44), LocalDateTime.of(2024,
                        Month.JUNE, 9, 22, 17, 44), 0, null)));

        Assertions.assertThrows(ShortURLNotFoundException.class, () -> urlCrudServiceImpl.deleteByShortIdAndUser("shortId", user));
    }

}
