package com.goit.url.v2;

import com.goit.auth.User;
import com.goit.exception.LogEnum;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ShortURLGenerationService {

    private final UrlRepository urlRepository;

    @Autowired
    public ShortURLGenerationService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String generateShortURL(User user) {
        Optional<Long> maxIdOptional = urlRepository.getMaxId();
        long maxId = maxIdOptional.orElse(0L) + 1;

        String userHexId = Long.toHexString(user.getId());
        String maxIdHex = Long.toHexString(maxId);

        String shortUrl = userHexId + maxIdHex;

        log.info("{}: short url for user (id: {}) was created", LogEnum.SERVICE, user.getId());
        return shortUrl.replaceAll("^0[xX]+", "");
    }

}
