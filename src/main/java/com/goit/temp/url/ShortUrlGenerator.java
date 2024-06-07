package com.goit.temp.url;


import com.goit.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class ShortUrlGenerator {
    private final UrlRepository urlRepository;

    @Autowired
    public ShortUrlGenerator(UrlRepository urlRepository) {
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
