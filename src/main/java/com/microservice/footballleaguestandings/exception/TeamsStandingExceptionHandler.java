package com.microservice.footballleaguestandings.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@ControllerAdvice
public class TeamsStandingExceptionHandler {

    @ExceptionHandler(TeamsStandingException.class)
    public ResponseEntity<RestApiError> handleBadRequestException(Exception ex) {
        log.error("Exception caught: ", ex);
        return new ResponseEntity<>(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, HttpClientErrorException.class})
    public ResponseEntity<RestApiError> handleException(Exception ex) {
        log.error("Exception caught: ", ex);
        return new ResponseEntity<>(new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<RestApiError> handleValidationException(Exception ex) {
        log.error("Exception caught: ", ex);
        return new ResponseEntity<>(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}