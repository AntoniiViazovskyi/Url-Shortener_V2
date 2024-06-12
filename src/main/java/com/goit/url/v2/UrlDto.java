package com.goit.url.v2;

import com.goit.auth.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlDto {
    private Long id;
    private User user;
    private String shortId;
    private String longURL;
    private LocalDateTime creationDate;
    private LocalDateTime expiryDate;
    private int clickCount;
}
