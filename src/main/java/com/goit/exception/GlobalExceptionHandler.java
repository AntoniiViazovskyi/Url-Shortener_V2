package com.goit.exception;

import com.goit.exception.exceptions.generalExceptions.UnauthorizedAccessException;
import com.goit.exception.exceptions.longURLExceptions.InvalidLongURLException;
import com.goit.exception.exceptions.longURLExceptions.LongURLAlreadyExistException;
import com.goit.exception.exceptions.longURLExceptions.NotValidLongURLException;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLAlreadyExistException;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLHasExpired;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLNotFoundException;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLRedirectionException;
import com.goit.exception.exceptions.userExceptions.UserAlreadyExistException;
import com.goit.exception.exceptions.userExceptions.UserIncorrectPasswordException;
import com.goit.exception.exceptions.userExceptions.UserNotFoundException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /* Validation exceptions */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, List<String>> result = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> {
                    result.computeIfAbsent(e.getField(), k -> new ArrayList<>()).add(e.getDefaultMessage());
                });
        return new ResponseEntity<>(getErrorsMap(result), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    /* General exceptions */

    @ExceptionHandler(value = {HttpMessageNotReadableException.class, MalformedJwtException.class})
    public ResponseEntity<Map<String, List<String>>> unauthorizedAccessException(HttpMessageNotReadableException e) {
        return getErrorsMap(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UnauthorizedAccessException.class})
    public ResponseEntity<Map<String, List<String>>> unauthorizedAccessException(UnauthorizedAccessException e) {
        return getErrorsMap(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Map<String, List<String>>> unauthorizedAccessException(BadCredentialsException e) {
        return getErrorsMap(e, HttpStatus.UNAUTHORIZED);
    }

    /* User exceptions */

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> userNotFoundException(UserNotFoundException e) {
        return getErrorsMap(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Map<String, List<String>>> unauthorizedAccessException(UserAlreadyExistException e) {
        return getErrorsMap(e, HttpStatus.CONFLICT);
    }

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

    @ExceptionHandler(ShortURLNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> shortURLNotFoundExpired(ShortURLNotFoundException e) {
        return getErrorsMap(e, HttpStatus.NOT_FOUND);
    }


    /* Helpers */

    private Map<String, List<String>> getErrorsMap(Map<String, List<String>> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", new ArrayList<>());
        errors.forEach((key, value) -> {
            value.forEach(v -> errorResponse.get("errors").add(String.format("%s: %s", key, v)));
        });
        return errorResponse;
    }

    private ResponseEntity<Map<String, List<String>>> getErrorsMap(Throwable ex, HttpStatus status) {
        Map<String, List<String>> map = new HashMap<>();
        map.put("errors", Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(map, new HttpHeaders(), status);
    }
}
