package com.goit.request;

import org.junit.jupiter.api.Test;
import java.sql.Date;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateShortUrlRequestTest {

    @Test
    public void testCreateShortUrlRequest() {
        String shortId = "abc123";
        String longUrl = "https://example.com";
        Date expirationAt = new java.sql.Date(System.currentTimeMillis());

        CreateShortUrlRequest request = new CreateShortUrlRequest(shortId, longUrl, expirationAt);

        assertThat(request.getShortId()).isEqualTo(shortId);
        assertThat(request.getLongUrl()).isEqualTo(longUrl);
        assertThat(request.getExpiration_at()).isEqualTo(expirationAt);
    }

    @Test
    public void testSetterMethods() {
        CreateShortUrlRequest request = new CreateShortUrlRequest();

        String shortId = "def456";
        String longUrl = "https://example.org";
        Date expirationAt = new java.sql.Date(System.currentTimeMillis());
        request.setShortId(shortId);
        request.setLongUrl(longUrl);
        request.setExpiration_at(expirationAt);

        assertThat(request.getShortId()).isEqualTo(shortId);
        assertThat(request.getLongUrl()).isEqualTo(longUrl);
        assertThat(request.getExpiration_at()).isEqualTo(expirationAt);
    }
}