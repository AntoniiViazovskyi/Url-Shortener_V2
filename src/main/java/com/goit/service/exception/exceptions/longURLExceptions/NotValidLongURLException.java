package com.goit.service.exception.exceptions.longURLExceptions;

public class NotValidLongURLException extends Exception{
    private static final String NOT_VALID_URL = """
            Url %s isn't valid
            Please check if it is real url
            """;

    public NotValidLongURLException(String longURL) {
        super(String.format(NOT_VALID_URL, longURL));
    }
}
