package com.goit.url.V2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UrlCrudService {
    ShortURLDTO createShortURL(ShortURLDTO shortURLDTO);

    Optional<ShortURLDTO> getShortURLById(Long id);

    List<ShortURLDTO> getAllShortURLsByCreatorId(Long userId);

    ShortURLDTO updateShortURL(ShortURLDTO shortURLDTO);

    void deleteShortURL(Long id);

    String redirectShortURL(String shortUrl);


}
