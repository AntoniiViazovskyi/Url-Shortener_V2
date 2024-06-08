package com.goit.url.V2;

import com.goit.response.UrlResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<UrlResponse> toUtlResponseList(Collection<Url> entities) {
        return entities.stream()
                .map(this::toUrlResponse)
                .collect(Collectors.toList());
    }

    public UrlResponse toUrlResponse(Url entity) {
        UrlResponse dto = new UrlResponse();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setShortId(entity.getShortId());
        dto.setLongUrl(entity.getLongUrl());
        dto.setCreationDate(entity.getCreationDate());
        dto.setExpiryDate(entity.getExpiryDate());
        dto.setClickCount(entity.getClickCount());
        return dto;
    }
}
