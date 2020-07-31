package com.example.restservice.exceptions;

public class BpdtsServiceException extends RuntimeException{
    public BpdtsServiceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
