package com.david.eureka;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@ControllerAdvice
@RequestMapping(produces = "application/json")
public class UserControllerAdvice {

    private ResponseEntity < ? > error(final Exception exception, final HttpStatus httpStatus, final String logRef) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        Error error = getError(exception);
        return new ResponseEntity< >(error, httpStatus);
    }

    private Error getError(Exception exception) {
        Error error = new Error();
        error.setCode(0);
        error.setMessage(exception.getMessage());
        return error;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity < ? > assertionException(final IllegalArgumentException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    }

    @Data
    class Error{
        int code;
        String message;
    }
}