package com.goit.url.V2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.goit.auth.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ShortURLRepositoryTest {

    @Mock
    private ShortURLRepository shortURLRepository;

    @InjectMocks
    private ShortURLGenerationService shortURLGenerationService;

    @Test
    public void testFindByUrl() {
        String url = "example.com";
        ShortURL shortURL = new ShortURL();
        shortURL.setUrl(url);

        when(shortURLRepository.findByUrl(url)).thenReturn(Optional.of(shortURL));
        Optional<ShortURL> foundShortURL = shortURLRepository.findByUrl(url);

        assertThat(foundShortURL).isPresent();
        assertThat(foundShortURL.get().getUrl()).isEqualTo(url);
    }

    @Test
    public void testFindAllByCreatorId() {
        Long userId = 1L;
        ShortURL shortURL1 = new ShortURL();
        shortURL1.setCreator(new User(userId));
        ShortURL shortURL2 = new ShortURL();
        shortURL2.setCreator(new User(userId));
        List<ShortURL> shortURLs = new ArrayList<>();
        shortURLs.add(shortURL1);
        shortURLs.add(shortURL2);

        when(shortURLRepository.findAllByCreatorId(userId)).thenReturn(shortURLs);
        List<ShortURL> foundShortURLs = shortURLRepository.findAllByCreatorId(userId);

        assertThat(foundShortURLs).hasSize(2);
        assertThat(foundShortURLs.get(0).getCreator().getId()).isEqualTo(userId);
        assertThat(foundShortURLs.get(1).getCreator().getId()).isEqualTo(userId);
    }

    @Test
    public void testGetMaxId() {
        Long maxId = 10L;

        when(shortURLRepository.getMaxId()).thenReturn(Optional.of(maxId));
        Optional<Long> foundMaxId = shortURLRepository.getMaxId();

        assertThat(foundMaxId).isPresent();
        assertThat(foundMaxId.get()).isEqualTo(maxId);
    }
}