package com.goit.exception;

import com.goit.exception.exceptions.generalExceptions.UnauthorizedAccessException;
import com.goit.exception.exceptions.longURLExceptions.InvalidLongURLException;
import com.goit.exception.exceptions.longURLExceptions.LongURLAlreadyExistException;
import com.goit.exception.exceptions.longURLExceptions.NotValidLongURLException;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLAlreadyExistException;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLHasExpired;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLRedirectionException;
import com.goit.exception.exceptions.userExceptions.UserIncorrectPasswordException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, List<String>> result = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> {
                    if (result.containsKey(e.getField())) {
                        result.get(e.getField()).add(e.getDefaultMessage());
                    } else {
                        result.put(e.getField(), Collections.singletonList(e.getDefaultMessage()));
                    }
                });
        return new ResponseEntity<>(getErrorsMap(result), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    /* General exceptions */

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, List<String>>> unauthorizedAccessException(UnauthorizedAccessException e) {
        return getErrorsMap(e, HttpStatus.UNAUTHORIZED);
    }

    /* Common exceptions */

//    @ExceptionHandler(value = {
//            UserAlreadyExistException.class,
//            LongURLAlreadyExistException.class,
//            ShortURLAlreadyExistException.class
//    })
//    public ResponseEntity<Map<String, List<String>>> user_LongUrl_ShortURLAlreadyExistException(Exception e) {
//        return getErrorsMap(e, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(value = {
//            UserAlreadyExistException.class,
//            LongURLAlreadyExistException.class,
//            ShortURLAlreadyExistException.class
//    })
//    public ResponseEntity<Map<String, List<String>>> user_LongUrl_ShortURLNotFoundException(Exception e) {
//        return getErrorsMap(e, HttpStatus.NOT_FOUND);
//    }

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

    private Map<String, List<String>> getErrorsMap(Map<String, List<String>> errors) {
//    private Map<String, Map<String, List<String>>> getErrorsMap(Map<String, List<String>> errors) {
//        Map<String, Map<String, List<String>>> errorResponse = new HashMap<>();
//        errorResponse.put("errors", errors);
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", new ArrayList<>());
        errors.forEach((key, value) -> {
            errorResponse.get("errors").add(String.format("%s: %s", key, value.getFirst()));
        });
        return errorResponse;
    }

    private ResponseEntity<Map<String, List<String>>> getErrorsMap(Throwable ex, HttpStatus status) {
        Map<String, List<String>> map = new HashMap<>();
        map.put("errors", Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(map, new HttpHeaders(), status);
    }
}
