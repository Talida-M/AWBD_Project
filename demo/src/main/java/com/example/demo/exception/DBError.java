package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class DBError extends BaseException {
    public DBError(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
