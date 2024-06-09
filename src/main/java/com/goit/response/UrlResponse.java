package com.goit.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlResponse {

    private String shortId;
    private String longUrl;
    private LocalDateTime expiryDate;
}
