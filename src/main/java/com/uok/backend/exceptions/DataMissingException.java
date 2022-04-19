package com.uok.backend.exceptions;

public class DataMissingException extends Exception {
    public DataMissingException() {
        super("Input Data Missing");
    }

    public DataMissingException(String message) {
        super(message);
    }
}
