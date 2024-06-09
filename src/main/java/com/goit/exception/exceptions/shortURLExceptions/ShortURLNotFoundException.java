package com.goit.exception.exceptions.shortURLExceptions;

public class ShortURLNotFoundException extends Exception{
    private static final String SHORT_URL_NOT_FOUND_EXCEPTION_TEXT = "Short url with id = %s not found.";
    private static final String CAN_NOT_UPDATE_SHORT_URL_WITHOUT_ID_EXCEPTION_TEXT = "Can not find short url without id.";

    public ShortURLNotFoundException() {
        super(CAN_NOT_UPDATE_SHORT_URL_WITHOUT_ID_EXCEPTION_TEXT);
    }

    public ShortURLNotFoundException(String shortId) {
        super(String.format(SHORT_URL_NOT_FOUND_EXCEPTION_TEXT, shortId));
    }
}
