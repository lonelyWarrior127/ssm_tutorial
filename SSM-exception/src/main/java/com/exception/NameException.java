package com.exception;

public class NameException extends MyUserException{
    public NameException() {
        super();
    }

    public NameException(String message) {
        super(message);
    }
}
