package com.goit.url.V2;

import com.goit.auth.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ShortURLTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidShortURL() {
        String url = "shorturl";
        String longUrl = "https://example.com";
        User creator = new User();
        List<User> users = new ArrayList<>();
        LocalDate createdDate = LocalDate.now();
        Long clickCount = 0L;
        LocalDate expiryDate = LocalDate.now().plusDays(7);

        ShortURL shortURL = ShortURL.builder()
                .url(url)
                .longURL(longUrl)
                .creator(creator)
                .users(users)
                .createdDate(createdDate)
                .clickCount(clickCount)
                .expiryDate(expiryDate)
                .build();

        Set<ConstraintViolation<ShortURL>> violations = validator.validate(shortURL);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testShortURLWithNullCreator() {
        String url = "shorturl";
        String longUrl = "https://example.com";
        User creator = null;
        List<User> users = new ArrayList<>();
        LocalDate createdDate = LocalDate.now();
        Long clickCount = 0L;
        LocalDate expiryDate = LocalDate.now().plusDays(7);

        ShortURL shortURL = ShortURL.builder()
                .url(url)
                .longURL(longUrl)
                .creator(creator)
                .users(users)
                .createdDate(createdDate)
                .clickCount(clickCount)
                .expiryDate(expiryDate)
                .build();

        Set<ConstraintViolation<ShortURL>> violations = validator.validate(shortURL);
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void testShortURLWithNullExpiryDate() {
        String url = "shorturl";
        String longUrl = "https://example.com";
        User creator = new User();
        List<User> users = new ArrayList<>();
        LocalDate createdDate = LocalDate.now();
        Long clickCount = 0L;
        LocalDate expiryDate = null;

        ShortURL shortURL = ShortURL.builder()
                .url(url)
                .longURL(longUrl)
                .creator(creator)
                .users(users)
                .createdDate(createdDate)
                .clickCount(clickCount)
                .expiryDate(expiryDate)
                .build();

        Set<ConstraintViolation<ShortURL>> violations = validator.validate(shortURL);
        assertThat(violations).isNotEmpty();
    }

    @Test
    public void testShortURLWithInvalidFields() {
        String url = null;
        String longUrl = null;
        User creator = null;
        List<User> users = new ArrayList<>();
        LocalDate createdDate = LocalDate.now();
        Long clickCount = 0L;
        LocalDate expiryDate = null;

        ShortURL shortURL = ShortURL.builder()
                .url(url)
                .longURL(longUrl)
                .creator(creator)
                .users(users)
                .createdDate(createdDate)
                .clickCount(clickCount)
                .expiryDate(expiryDate)
                .build();

        Set<ConstraintViolation<ShortURL>> violations = validator.validate(shortURL);
        assertThat(violations).hasSize(4);

        for (ConstraintViolation<ShortURL> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            assertThat(propertyPath).isIn("url", "longURL", "creator", "expiryDate");
        }
    }
}
