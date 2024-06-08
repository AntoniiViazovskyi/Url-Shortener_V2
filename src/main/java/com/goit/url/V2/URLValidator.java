package com.goit.url.V2;

import com.goit.exception.LogEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class URLValidator {

    //Метод для перевірки синтаксичної коректності
    public static boolean isValid(String urlStr) {
        try {
            new URL(urlStr);
            log.info(String.format("%s Url %s has passed syntactics validation", LogEnum.VALIDATION, urlStr));
            return true;
        } catch (MalformedURLException e) {
            log.info(String.format("%s Url %s didn't pass syntactics validation", LogEnum.VALIDATION, urlStr));
            return false;
        }
    }

    //Метод для перевірки доступності
    public static boolean isAccessibleUrl(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();

            log.info(String.format("%s Url %s has passed accessibility validation", LogEnum.VALIDATION, url));
            return (200 <= responseCode && responseCode < 400);
        } catch (IOException e) {
            log.info(String.format("%s Url %s didn't pass accessibility validation", LogEnum.VALIDATION, url));
            return false;
        }
    }
}


