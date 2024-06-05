package com.goit.url;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UrlCrudServiceImpl implements UrlCrudService {
    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlMapper urlMapper;

    @Autowired
    private ShortUrlGeneratorUser shortUrlGenerator;

    @Override
    @Transactional
    public Url createShortUrl(DtoCreateUrlRequest request, User user) {
        if (!URLValidator.isValid(request.getLongUrl()) || !URLValidator.isAccessibleUrl(request.getLongUrl())) {
            throw new IllegalArgumentException("Invalid or inaccessible URL");
        }

        String shortUrl = shortUrlGenerator.generateShortUrl();
        Url url = urlMapper.toUrl(request, user);
        url.setShortUrl(shortUrl);
        url.setCreationDate(LocalDateTime.now());
        url.setClickCount(0L);

        return urlRepository.save(url);
    }

    @Override
    public Optional<Url> getShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }

    @Override
    public List<Url> getUserUrls(User user) {
        return urlRepository.findAllByUser(user);
    }

    @Override
    @Transactional
    public void incrementClickCount(String shortUrl) {
        Optional<Url> optionalUrl = getShortUrl(shortUrl);
        if (optionalUrl.isPresent()) {
            Url url = optionalUrl.get();
            url.setClickCount(url.getClickCount() + 1);
            urlRepository.save(url);
        } else {
            throw new IllegalArgumentException("Short URL not found");
        }
    }
}

