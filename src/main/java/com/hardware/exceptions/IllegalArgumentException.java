package com.hardware.exceptions;

public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String message) {
        super(message);
    }
    public IllegalArgumentException() {super("Illegal Argument");}
}
