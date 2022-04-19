package com.uok.backend.exceptions;

public class FailedLoginException extends Exception {

    public FailedLoginException(String message) {
        super(message);
    }
}
