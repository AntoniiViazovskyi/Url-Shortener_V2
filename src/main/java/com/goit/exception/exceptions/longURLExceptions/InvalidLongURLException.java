package com.goit.exception.exceptions.longURLExceptions;

public class InvalidLongURLException extends Exception{
    private static final String INCORRECT_FORMAT = """
            Your URL is not valid or has incorrect format.
            Url must be structured like this:
            <schema>://<login>:<password>@<host>:<port>/<path>?<parameters>#<anchor>
            Your URL:
            %s""";

    public InvalidLongURLException(String longURL) {
        super(String.format(INCORRECT_FORMAT, longURL));
    }
}
