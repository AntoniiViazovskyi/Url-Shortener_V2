package com.goit.service.exception.exceptions.longURLExceptions;

public class LongURLNotFoundException extends Exception{
    private static final String LONG_URL_NOT_FOUND_EXCEPTION_TEXT = "Long url with id = %s not found.";
    private static final String CAN_NOT_UPDATE_LONG_URL_WITHOUT_ID_EXCEPTION_TEXT = "Can not found long url without id.";

    public LongURLNotFoundException() {
        super(CAN_NOT_UPDATE_LONG_URL_WITHOUT_ID_EXCEPTION_TEXT);
    }

    public LongURLNotFoundException(Long id) {
        super(String.format(LONG_URL_NOT_FOUND_EXCEPTION_TEXT, id));
    }
}
