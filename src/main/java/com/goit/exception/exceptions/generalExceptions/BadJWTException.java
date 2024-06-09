package com.goit.exception.exceptions.generalExceptions;

public class BadJWTException extends Exception {
    private static final String NO_PERMISSION = "Bad JWT!";

    public BadJWTException() {
        super(NO_PERMISSION);
    }

}
