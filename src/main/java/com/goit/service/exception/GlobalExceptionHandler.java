package com.goit.service.exception;

import com.goit.service.exception.exceptions.generalExceptions.UnauthorizedAccessException;
import com.goit.service.exception.exceptions.longURLExceptions.InvalidLongURLException;
import com.goit.service.exception.exceptions.longURLExceptions.LongURLAlreadyExistException;
import com.goit.service.exception.exceptions.longURLExceptions.NotValidLongURLException;
import com.goit.service.exception.exceptions.shortURLExceptions.ShortURLAlreadyExistException;
import com.goit.service.exception.exceptions.shortURLExceptions.ShortURLHasExpired;
import com.goit.service.exception.exceptions.shortURLExceptions.ShortURLRedirectionException;
import com.goit.service.exception.exceptions.userExceptions.UserAlreadyExistException;
import com.goit.service.exception.exceptions.userExceptions.UserIncorrectPasswordException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* General exceptions */

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, List<String>>> unauthorizedAccessException(UnauthorizedAccessException e) {
        return getErrorsMap(e, HttpStatus.UNAUTHORIZED);
    }

    /* Common exceptions */

    @ExceptionHandler(value = {
            UserAlreadyExistException.class,
            LongURLAlreadyExistException.class,
            ShortURLAlreadyExistException.class
    })
    public ResponseEntity<Map<String, List<String>>> user_LongUrl_ShortURLAlreadyExistException(Exception e) {
        return getErrorsMap(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            UserAlreadyExistException.class,
            LongURLAlreadyExistException.class,
            ShortURLAlreadyExistException.class
    })
    public ResponseEntity<Map<String, List<String>>> user_LongUrl_ShortURLNotFoundException(Exception e) {
        return getErrorsMap(e, HttpStatus.NOT_FOUND);
    }

    /* User exceptions */

    @ExceptionHandler(UserIncorrectPasswordException.class)
    public ResponseEntity<Map<String, List<String>>> userIncorrectPasswordException(UserIncorrectPasswordException e) {
        return getErrorsMap(e, HttpStatus.UNAUTHORIZED);
    }

    /* LongURL exceptions */

    @ExceptionHandler(InvalidLongURLException.class)
    public ResponseEntity<Map<String, List<String>>> invalidLongURLException(InvalidLongURLException e) {
        return getErrorsMap(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotValidLongURLException.class)
    public ResponseEntity<Map<String, List<String>>> notValidLongURLException(NotValidLongURLException e) {
        return getErrorsMap(e, HttpStatus.BAD_REQUEST);
    }

    /* ShortURL exceptions */

    @ExceptionHandler(ShortURLRedirectionException.class)
    public ResponseEntity<Map<String, List<String>>> shortURLRedirectionException(ShortURLRedirectionException e) {
        return getErrorsMap(e, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ShortURLHasExpired.class)
    public ResponseEntity<Map<String, List<String>>> shortURLHasExpired(ShortURLHasExpired e) {
        return getErrorsMap(e, HttpStatus.GONE);
    }


    /* Helpers */

    private Map<String, Map<String, List<String>>> getErrorsMap(Map<String, List<String>> errors) {
        Map<String, Map<String, List<String>>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    private ResponseEntity<Map<String, List<String>>> getErrorsMap(Throwable ex, HttpStatus status) {
        Map<String, List<String>> map = new HashMap<>();
        map.put("errors", Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(map, new HttpHeaders(), status);
    }
}
