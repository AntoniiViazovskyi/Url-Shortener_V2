package com.goit.service.exception.exceptions.shortURLExceptions;

public class ShortURLAlreadyExistException extends Exception{
    private static final String SHORT_URL_ALREADY_EXIST_BY_ID = "Short URL with id = %s already exist.";
    private static final String SHORT_URL_ALREADY_EXIST_BY_URL = "Short URL with itself url = %s already exist.";

    public ShortURLAlreadyExistException(Long id) {
        super(String.format(SHORT_URL_ALREADY_EXIST_BY_ID, id));
    }
    public ShortURLAlreadyExistException(String url) {
        super(String.format(SHORT_URL_ALREADY_EXIST_BY_URL, url));
    }
}
