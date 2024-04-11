package com.example.demo.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("The search with id " + id + " doesn't exist.");
    }
}
