package com.goit.url.V2;

import org.springframework.stereotype.Component;

@Component
public class ShortURLMapper {
    public ShortURLDTO toDTO(ShortURL shortURL) {
        return ShortURLDTO.builder()
                .id(shortURL.getId())
                .url(shortURL.getUrl())
                .longURL(shortURL.getLongURL())
                .creator(shortURL.getCreator())
                .users(shortURL.getUsers())
                .expiryDate(shortURL.getExpiryDate())
                .clickCount(shortURL.getClickCount())
                .build();
    }

    public ShortURL toEntity(ShortURLDTO shortURLDTO) {
        return ShortURL.builder()
                .id(shortURLDTO.getId())
                .url(shortURLDTO.getUrl())
                .longURL(shortURLDTO.getLongURL())
                .creator(shortURLDTO.getCreator())
                .users(shortURLDTO.getUsers())
                .expiryDate(shortURLDTO.getExpiryDate())
                .clickCount(shortURLDTO.getClickCount())
                .build();
    }
}
