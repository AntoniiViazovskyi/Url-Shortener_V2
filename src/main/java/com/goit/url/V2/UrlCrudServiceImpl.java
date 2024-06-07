package com.goit.url.V2;


import com.goit.auth.User;
import com.goit.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    public UrlDto createURL(UrlDto urlDto) {
        if (!URLValidator.isValid(urlDto.getLongURL()) || !URLValidator.isAccessibleUrl(urlDto.getLongURL())) {
            throw new IllegalArgumentException("Invalid long URL");
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
    public Optional<UrlDto> getURLById(Long id) {
        return urlRepository.findById(id).map(urlMapper::toDTO);
    }

    @Override
    public List<UrlDto> getAllByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User '%s' not found", email)));

        return urlRepository.findAllByUserId(user.getId())
                .stream()
                .map(urlMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UrlDto> getAllActiveByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User '%s' not found", email)));

        return urlRepository.findAllActiveByUserId(user.getId())
                .stream()
                .map(urlMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void increaseClicksCount(String shortId) {
        urlRepository.incClicksCount(shortId);
    }

    @Override
    public void deleteByShortId(String shortId) {
        urlRepository.deleteUrlByShortId(shortId);
    }
}

