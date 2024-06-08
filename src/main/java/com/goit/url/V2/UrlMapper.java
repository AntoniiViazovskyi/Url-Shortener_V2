package com.goit.url.V2;

import com.goit.exception.LogEnum;
import com.goit.response.UrlResponse;
import com.goit.response.UrlStatsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UrlMapper {
    public UrlDto toDTO(Url url) {
        log.info(String.format("%s Url entity %s was mapped to UrlDto", LogEnum.MAPPER, url));
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
        log.info(String.format("%s UrlDto %s was mapped to Url entity", LogEnum.MAPPER, urlDto));
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

    public UrlStatsResponse toUrlStatsResponse(UrlDto urlDto) {
        log.info(String.format("%s UrlDto %s was mapped to UrlStatsResponse", LogEnum.MAPPER, urlDto));
        return UrlStatsResponse.builder()
                .shortId(urlDto.getShortId())
                .longUrl(urlDto.getLongURL())
                .clickCount(urlDto.getClickCount())
                .build();
    }

    public List<UrlResponse> toUtlResponseList(Collection<Url> entities) {
        log.info(String.format("%s Url's list: %s, was mapped to UrlResponse list", LogEnum.MAPPER, entities));
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

        log.info(String.format("%s Url entity %s was mapped to UrlResponse", LogEnum.MAPPER, entity));
        return dto;
    }
}
