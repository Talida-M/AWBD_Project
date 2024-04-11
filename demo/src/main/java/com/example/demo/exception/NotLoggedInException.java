package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class NotLoggedInException extends BaseException {

    public NotLoggedInException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}