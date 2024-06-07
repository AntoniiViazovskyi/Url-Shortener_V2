package com.goit.service.exception.exceptions.userExceptions;

public class UserIncorrectPasswordException extends Exception{
    private static final String USER_INCORRECT_PASSWORD_EXCEPTION_TEXT =
            "Incorrect password for user with username = %s.";

    public UserIncorrectPasswordException(String username) {
        super(String.format(USER_INCORRECT_PASSWORD_EXCEPTION_TEXT, username));
    }
}
