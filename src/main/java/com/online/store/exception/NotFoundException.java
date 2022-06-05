package com.online.store.exception;

public class NotFoundException extends RuntimeException {

    private static final String NOT_FOND = " not found";

    NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException notFoundException(String object) {
        return new NotFoundException(object + NOT_FOND);
    }

}
