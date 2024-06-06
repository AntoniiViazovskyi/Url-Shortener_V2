package com.goit.url;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class ShortUrlGeneratorUser {
    private final UrlRepository urlRepository;

    @Autowired
    public ShortUrlGeneratorUser(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String generateShortUrl(User user) {
        Optional<Long> maxIdOptional = urlRepository.getMaxId();
        long maxId = maxIdOptional.orElse(0L) + 1;

        String userIdHex = Long.toHexString(user.getId());
        String maxIdHex = Long.toHexString(maxId);

        String shortUrl = userIdHex + maxIdHex;
        return shortUrl;
    }
}
