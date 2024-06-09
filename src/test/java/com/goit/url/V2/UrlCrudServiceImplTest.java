package com.goit.url.V2;

import com.goit.auth.User;
import com.goit.auth.UserRepository;
import com.goit.url.V2.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
@ActiveProfiles("test")
public class UrlCrudServiceImplTest {

    @Mock
    private ShortURLRepository shortURLRepository;

    @Mock
    private ShortURLMapper shortURLMapper;

    @Mock
    private ShortURLGenerationService shortURLGenerationService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UrlCrudServiceImpl urlCrudService;


    @Test
    public void testCreateShortURL() {
        ShortURLDTO shortURLDTO = new ShortURLDTO();
        shortURLDTO.setLongURL("https://example.com");
        shortURLDTO.setCreator(new User());

        String generatedShortURL = "generated_short_url";
        ShortURL shortURL = new ShortURL();
        shortURL.setUrl(generatedShortURL);

        when(shortURLGenerationService.generateShortURL(any(User.class))).thenReturn(generatedShortURL);
        when(shortURLMapper.toEntity(any(ShortURLDTO.class))).thenReturn(shortURL);
        when(shortURLRepository.save(any(ShortURL.class))).thenReturn(shortURL);
        when(shortURLMapper.toDTO(any(ShortURL.class))).thenReturn(shortURLDTO);

        ShortURLDTO createdShortURL = urlCrudService.createShortURL(shortURLDTO);

        assertThat(createdShortURL).isNotNull();
        assertThat(createdShortURL.getUrl()).isEqualTo(generatedShortURL);
    }

    @Test
    public void testGetShortURLById() {
        Long id = 1L;
        ShortURLDTO expectedShortURLDTO = new ShortURLDTO();
        expectedShortURLDTO.setId(id);
        ShortURL expectedShortURL = new ShortURL();

        when(shortURLRepository.findById(id)).thenReturn(Optional.of(expectedShortURL));
        when(shortURLMapper.toDTO(expectedShortURL)).thenReturn(expectedShortURLDTO);

        Optional<ShortURLDTO> foundShortURLDTO = urlCrudService.getShortURLById(id);

        assertThat(foundShortURLDTO).isPresent();
        assertThat(foundShortURLDTO.get()).isEqualTo(expectedShortURLDTO);
    }

    @Test
    public void testGetAllShortURLsByCreatorId() {
        Long userId = 1L;
        ShortURLDTO shortURLDTO1 = new ShortURLDTO();
        shortURLDTO1.setCreator(new User(userId));
        ShortURLDTO shortURLDTO2 = new ShortURLDTO();
        shortURLDTO2.setCreator(new User(userId));
        List<ShortURLDTO> expectedShortURLs = Arrays.asList(shortURLDTO1, shortURLDTO2);

        when(shortURLRepository.findAllByCreatorId(userId)).thenReturn(Arrays.asList(new ShortURL(), new ShortURL()));
        when(shortURLMapper.toDTO(any(ShortURL.class))).thenReturn(shortURLDTO1).thenReturn(shortURLDTO2);

        List<ShortURLDTO> foundShortURLs = urlCrudService.getAllShortURLsByCreatorId(userId);

        assertThat(foundShortURLs).hasSize(2);
        assertThat(foundShortURLs).containsExactlyElementsOf(expectedShortURLs);
    }

    @Test
    public void testUpdateShortURL() {
        Long id = 1L;
        ShortURLDTO shortURLDTO = new ShortURLDTO();
        shortURLDTO.setId(id);

        ShortURL shortURL = new ShortURL();
        shortURL.setId(id);

        when(shortURLRepository.save(any(ShortURL.class))).thenReturn(shortURL);
        when(shortURLMapper.toEntity(shortURLDTO)).thenReturn(shortURL);
        when(shortURLMapper.toDTO(shortURL)).thenReturn(shortURLDTO);

        ShortURLDTO updatedShortURL = urlCrudService.updateShortURL(shortURLDTO);

        assertThat(updatedShortURL).isNotNull();
        assertThat(updatedShortURL.getId()).isEqualTo(id);
    }

    @Test
    public void testDeleteShortURL() {
        Long id = 1L;

        urlCrudService.deleteShortURL(id);

        verify(shortURLRepository).deleteById(id);
    }

    @Test
    public void testRedirectShortURL() {
        String shortUrl = "short-url";
        ShortURL shortURL = new ShortURL();
        shortURL.setLongURL("https://example.com");
        shortURL.setClickCount(0L);

        when(shortURLRepository.findByUrl(shortUrl)).thenReturn(Optional.of(shortURL));

        String redirectUrl = urlCrudService.redirectShortURL(shortUrl);

        assertThat(redirectUrl).isEqualTo(shortURL.getLongURL());
    }
}