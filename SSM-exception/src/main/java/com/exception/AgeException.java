package com.exception;

public class AgeException extends MyUserException{
    public AgeException() {
        super();
    }

    public AgeException(String message) {
        super(message);
    }
}
