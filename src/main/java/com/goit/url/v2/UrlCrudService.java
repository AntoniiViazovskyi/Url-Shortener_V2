package com.goit.url.v2;

import com.goit.auth.User;
import com.goit.exception.exceptions.longURLExceptions.InvalidLongURLException;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UrlCrudService {
    UrlDto createURL(UrlDto urlDto) throws InvalidLongURLException;

    Optional<UrlDto> getURLById(Long id);
    Optional<UrlDto> getURLByShortId(String shortId);
    Optional<UrlDto> getURLByShortIdAndUser(String shortId, User user);

    List<UrlDto> getAllByUserId();
    List<UrlDto> getAllActiveByUserId();
    void updateClicksCount(String shortId);
    void deleteByShortIdAndUser(String shortId, User user) throws ShortURLNotFoundException;
}
