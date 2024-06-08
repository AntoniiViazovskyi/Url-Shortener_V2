package com.goit.url.V2;


import com.goit.auth.User;
import com.goit.auth.UserRepository;
import com.goit.exception.LogEnum;
import com.goit.exception.exceptions.longURLExceptions.InvalidLongURLException;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLNotFoundException;
import com.goit.exception.exceptions.userExceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UrlCrudServiceImpl implements UrlCrudService {

    private final UrlRepository urlRepository;
    private final UrlMapper urlMapper;
    private final ShortURLGenerationService shortURLGenerationService;
    private UserRepository userRepository;


    @Override
    public UrlDto createURL(UrlDto urlDto) throws InvalidLongURLException {
        if (!URLValidator.isValid(urlDto.getLongURL()) || !URLValidator.isAccessibleUrl(urlDto.getLongURL())) {
            throw new InvalidLongURLException(urlDto.getLongURL());
        }

        String generatedShortURL = shortURLGenerationService.generateShortURL(urlDto.getUser());
        urlDto.setShortId(generatedShortURL);

        Url url = urlMapper.toEntity(urlDto);
        Url savedUrl = urlRepository.save(url);

        log.info(String.format("%s: request on saving Url %s was created", LogEnum.SERVICE, url));
        return urlMapper.toDTO(savedUrl);
    }

    @Override
    public Optional<UrlDto> getURLByShortId(String shortId) {
        log.info(String.format("%s request on retrieving url by id %s was sent", LogEnum.SERVICE, shortId));
        return urlRepository.findByShortId(shortId).map(urlMapper::toDTO);
    }

    @Override
    public Optional<UrlDto> getURLByShortIdAndUser(String shortId, User user) {
        log.info(String.format("%s request on retrieving user's (id: %s) url by urlId (%s) was sent", LogEnum.SERVICE, user.getId(), shortId));
        return urlRepository.findByShortIdAndUser(shortId, user).map(urlMapper::toDTO);
    }

    @Override
    public Optional<UrlDto> getURLById(Long id) {
        log.info(String.format("%s request on retrieving url by urlId (%s) was sent", LogEnum.SERVICE, id));
        return urlRepository.findById(id).map(urlMapper::toDTO);
    }

    @Override
    public List<UrlDto> getAllByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));

        log.info(String.format("%s request on retrieving all user's (id: %s) urls was sent", LogEnum.SERVICE, user.getId()));
        return urlRepository.findAllByUser(user)
                .stream()
                .map(urlMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UrlDto> getAllActiveByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));

        log.info(String.format("%s request on retrieving all user's (%s) active urls was sent", LogEnum.SERVICE, user));
        return urlRepository.findActiveUrlsByUserId(user, LocalDateTime.now())
                .stream()
                .map(urlMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateClicksCount(String shortId) {
        log.info(String.format("%s request on increasing url's (shortUrl id: %s) click count was sent", LogEnum.SERVICE, shortId));
        urlRepository.incrementClickCount(shortId);
    }

    @Override
    public void deleteByShortIdAndUser(String shortId, User user) throws ShortURLNotFoundException {
        if (urlRepository.deleteUrlByShortIdAndUser(shortId, user) <= 0) {
                throw new ShortURLNotFoundException(shortId);
        }
    }
}

