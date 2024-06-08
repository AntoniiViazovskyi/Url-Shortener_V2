package com.goit.exception.exceptions.userExceptions;

public class UserNotFoundException extends RuntimeException{
    private static final String USER_WITH_USERNAME_NOT_FOUND_EXCEPTION_TEXT = "User with username/email = %s not found.";
    private static final String USER_WITH_ID_NOT_FOUND_EXCEPTION_TEXT = "User with id = %s not found.";


    public UserNotFoundException(String usernameOrEmail) {
        super(String.format(USER_WITH_USERNAME_NOT_FOUND_EXCEPTION_TEXT, usernameOrEmail));
    }

    public UserNotFoundException(Long id) {
        super(String.format(USER_WITH_ID_NOT_FOUND_EXCEPTION_TEXT, id));
    }
}
