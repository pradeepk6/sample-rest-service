package com.example.restservice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

@ControllerAdvice
public class ErrorAdvice {
    Logger logger = LoggerFactory.getLogger(ErrorAdvice.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({IllegalArgumentException.class, SQLException.class, NullPointerException.class})
    public void handle(Exception e) {
        logger.error(e.getMessage(), e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class, TypeMismatchException.class})
    public void handle(ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
    }

}
