package com.goit.url;

import com.goit.auth.User;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {
    public Url toUrl(DtoCreateUrlRequest request, User user) {
        return Url.builder()
                .longUrl(request.getLongUrl())
                .expiryDate(request.getExpiryDate())
                .user(user)
                .build();
    }
}
