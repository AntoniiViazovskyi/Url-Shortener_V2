package com.goit.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlResponse {

    private Long id;
    private String userId;
    private String shortId;
    private String longUrl;
    private int click_count;
    private Date created_at;
    private Date expiration_at;
}
