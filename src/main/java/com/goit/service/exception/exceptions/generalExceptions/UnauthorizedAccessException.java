package com.goit.service.exception.exceptions.generalExceptions;

import java.util.List;

public class UnauthorizedAccessException extends Exception{
    private static final String NO_PERMISSION = "You don't have permission to this data";
    private static final String NON_CREATOR = """
            You are not creator of this link. Users can edit only their own links.
            The id's of your links: %s
            """;


    public UnauthorizedAccessException() {
        super(NO_PERMISSION);
    }

    public UnauthorizedAccessException(List<Long> id) {
        super(String.format(NON_CREATOR, id));
    }
}
