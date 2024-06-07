package com.goit.temp.url;

import com.goit.auth.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UrlCrudService {
    Url createShortUrl(DtoCreateUrlRequest request, User user);
    Optional<Url> getShortUrl(String shortUrl);
    List<Url> getUserUrls(User user);
    void incrementClickCount(String shortUrl);
}
