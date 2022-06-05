package com.online.store.exception;

public class AlreadyExistException extends RuntimeException {

    private static final String EXISTS = " is already exists";

    AlreadyExistException(String message) {
        super(message);
    }

    public static AlreadyExistException isExistsException(String object) {
        return new AlreadyExistException(object + EXISTS);
    }

}
