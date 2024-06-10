package com.goit.url.V2;

import com.goit.response.UrlStatsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@ActiveProfiles("tests")
class UrlMapperTest {
    private UrlMapper urlMapper;

    @Value("${app.domain}")
    private String appDomain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        urlMapper = new UrlMapper();
    }
    @Test
    void testToDTOMapping() {
        Url url = new Url();
        url.setId(1L);
        url.setShortId("shortId");
        url.setLongUrl("longUrl");
        url.setCreationDate(LocalDateTime.now());
        url.setExpiryDate(LocalDateTime.now().plusDays(1));
        url.setClickCount(0);

        UrlDto urlDto = urlMapper.toDTO(url);

        assertEquals(url.getId(), urlDto.getId());
        assertEquals(url.getShortId(), urlDto.getShortId());
        assertEquals(url.getLongUrl(), urlDto.getLongURL());
        assertEquals(url.getCreationDate(), urlDto.getCreationDate());
        assertEquals(url.getExpiryDate(), urlDto.getExpiryDate());
        assertEquals(url.getClickCount(), urlDto.getClickCount());
    }

    @Test
    void testToEntityMapping() {
        UrlDto urlDto = new UrlDto();
        urlDto.setId(1L);
        urlDto.setShortId("shortId");
        urlDto.setLongURL("longUrl");
        urlDto.setCreationDate(LocalDateTime.now());
        urlDto.setExpiryDate(LocalDateTime.now().plusDays(1));
        urlDto.setClickCount(0);

        Url url = urlMapper.toEntity(urlDto);

        assertEquals(urlDto.getId(), url.getId());
        assertEquals(urlDto.getShortId(), url.getShortId());
        assertEquals(urlDto.getLongURL(), url.getLongUrl());
        assertEquals(urlDto.getCreationDate(), url.getCreationDate());
        assertEquals(urlDto.getExpiryDate(), url.getExpiryDate());
        assertEquals(urlDto.getClickCount(), url.getClickCount());
    }

    @Test
    void testToUrlStatsResponseMapping() {
        UrlDto urlDto = new UrlDto();
        urlDto.setId(1L);
        urlDto.setShortId("shortId");
        urlDto.setLongURL("longUrl");
        urlDto.setClickCount(0);

        UrlStatsResponse urlStatsResponse = urlMapper.toUrlStatsResponse(urlDto);

        assertEquals(urlDto.getShortId(), urlStatsResponse.getShortId());
        assertEquals(urlDto.getLongURL(), urlStatsResponse.getLongUrl());
        assertEquals(urlDto.getClickCount(), urlStatsResponse.getClickCount());
    }

//    @Test
//    void testToUrlResponseMapping() {
//        Url entity = new Url();
//        entity.setId(1L);
//        entity.setShortId("shortId");
//        entity.setLongUrl("longUrl");
//        entity.setCreationDate(LocalDateTime.now());
//        entity.setExpiryDate(LocalDateTime.now().plusDays(1));
//        entity.setClickCount(0);
//        entity.setUser(new User(1L, "email", "password", null, null));
//
//        UrlResponse urlResponse = urlMapper.toUrlResponse(entity);
//
//        assertEquals(entity.getId(), urlResponse.getId());
//        assertEquals(entity.getUser().getId(), urlResponse.getUserId());
//        assertEquals(entity.getShortId(), urlResponse.getShortId());
//        assertEquals(entity.getLongUrl(), urlResponse.getLongUrl());
//        assertEquals(entity.getCreationDate(), urlResponse.getCreationDate());
//        assertEquals(entity.getExpiryDate(), urlResponse.getExpiryDate());
//        assertEquals(entity.getClickCount(), urlResponse.getClickCount());
//    }
}