package com.goit.service.exception.exceptions.shortURLExceptions;

public class ShortURLRedirectionException extends Exception{
    private static final String CANNOT_REDIRECT = "Cannot redirect. Check the validity of the data";
    private static final String CANNOT_REDIRECT_WITH_URL = """
            There is no way to perform a redirect to %s.
            Please check the expiration date of the short url
            """;

    public ShortURLRedirectionException() {
        super(CANNOT_REDIRECT);
    }

    public ShortURLRedirectionException(String shortURL) {
        super(String.format(CANNOT_REDIRECT_WITH_URL, shortURL));
    }
}
