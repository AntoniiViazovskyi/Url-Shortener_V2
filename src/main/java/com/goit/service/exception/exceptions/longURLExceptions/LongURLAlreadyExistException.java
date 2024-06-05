package com.goit.service.exception.exceptions.longURLExceptions;

public class LongURLAlreadyExistException extends Exception{
    private static final String LONG_URL_ALREADY_EXIST_BY_ID = "Long URL with id = %s already exist.";
    private static final String LONG_URL_ALREADY_EXIST_BY_URL = "Long URL with itself url = %s already exist.";

    public LongURLAlreadyExistException(Long id) {
        super(String.format(LONG_URL_ALREADY_EXIST_BY_ID, id));
    }
    public LongURLAlreadyExistException(String url) {
        super(String.format(LONG_URL_ALREADY_EXIST_BY_URL, url));
    }
}
