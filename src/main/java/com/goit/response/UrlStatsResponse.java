package com.goit.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlStatsResponse {

    private String shortId;
    private String shortUrl;
    private String longUrl;
    private int clickCount;
}
