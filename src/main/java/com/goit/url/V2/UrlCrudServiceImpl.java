package com.goit.url.V2;


import com.goit.auth.User;
import com.goit.auth.UserRepository;
import com.goit.exception.exceptions.longURLExceptions.InvalidLongURLException;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            throw new InvalidLongURLException("Invalid long URL");
        }

        String generatedShortURL = shortURLGenerationService.generateShortURL(urlDto.getUser());
        urlDto.setShortId(generatedShortURL);

        Url url = urlMapper.toEntity(urlDto);
        Url savedUrl = urlRepository.save(url);

        return urlMapper.toDTO(savedUrl);
    }

    @Override
    public Optional<UrlDto> getURLByShortId(String shortId) {
        return urlRepository.findByShortId(shortId).map(urlMapper::toDTO);
    }

    @Override
    public Optional<UrlDto> getURLByShortIdAndUser(String shortId, User user) {
        return urlRepository.findByShortIdAndUser(shortId, user).map(urlMapper::toDTO);
    }

    @Override
    public Optional<UrlDto> getURLById(Long id) {
        return urlRepository.findById(id).map(urlMapper::toDTO);
    }

    @Override
    public List<UrlDto> getAllByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(email));

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
                new UsernameNotFoundException(email));

        return urlRepository.findActiveUrlsByUserId(user, LocalDateTime.now())
                .stream()
                .map(urlMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateClicksCount(String shortId) {
        urlRepository.incrementClickCount(shortId);
    }

    @Override
    public void deleteByShortIdAndUser(String shortId, User user) throws ShortURLNotFoundException {
        if (urlRepository.deleteUrlByShortIdAndUser(shortId, user) <= 0) {
                throw new ShortURLNotFoundException(shortId);
        }
    }
}

