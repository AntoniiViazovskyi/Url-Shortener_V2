package com.goit.url.V2;

import com.goit.auth.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private Long clickCount;
}
