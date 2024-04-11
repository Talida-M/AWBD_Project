package com.example.demo.exception;

public class DuplicateException  extends RuntimeException {
    public DuplicateException() {
            super("An entry with the same data already exists.");
        }
}
