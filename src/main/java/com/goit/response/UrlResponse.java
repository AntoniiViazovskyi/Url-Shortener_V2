package com.goit.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlResponse {

    private String shortId;
    private String shortUrl;
    private String longUrl;
    private LocalDateTime expiryDate;
}
