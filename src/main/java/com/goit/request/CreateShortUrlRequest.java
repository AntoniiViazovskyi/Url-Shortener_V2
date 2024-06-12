package com.goit.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateShortUrlRequest {

    @URL
    private String longUrl;

    @DateTimeFormat
    private LocalDateTime expiryDate;
}
