package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class RegisterException extends BaseException  {
    public RegisterException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
