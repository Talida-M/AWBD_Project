package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedUserException extends BaseException {
    public UnauthorizedUserException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
