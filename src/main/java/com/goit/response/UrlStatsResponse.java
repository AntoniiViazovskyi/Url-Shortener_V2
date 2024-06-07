package com.goit.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlStatsResponse {

    private String shortId;
    private String longUrl;
    private int click_count;
}
