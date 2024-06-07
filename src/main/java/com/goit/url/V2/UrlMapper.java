package com.goit.url.V2;

import org.springframework.stereotype.Component;

@Component
public class UrlMapper {
    public UrlDto toDTO(Url url) {
        return UrlDto.builder()
                .id(url.getId())
                .user(url.getUser())
                .shortId(url.getShortId())
                .longURL(url.getLongUrl())
                .creationDate(url.getCreationDate())
                .expiryDate(url.getExpiryDate())
                .clickCount(url.getClickCount())
                .build();
    }

    public Url toEntity(UrlDto urlDto) {
        return Url.builder()
                .id(urlDto.getId())
                .user(urlDto.getUser())
                .shortId(urlDto.getShortId())
                .longUrl(urlDto.getLongURL())
                .creationDate(urlDto.getCreationDate())
                .expiryDate(urlDto.getExpiryDate())
                .clickCount(urlDto.getClickCount())
                .build();
    }
}
