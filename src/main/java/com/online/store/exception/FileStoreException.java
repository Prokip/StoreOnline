package com.online.store.exception;

public class FileStoreException extends RuntimeException {

    private static final String STORE_THE_FILE_ERROR = "Could not store the file. Error: ";
    private static final String READ_THE_FILE = "Could not read the file!";
    private static final String ERROR = "Failed to determine URL. Reason: ";

    FileStoreException(String message) {
        super(message);
    }

    public static FileStoreException storeFileException(String object) {
        return new FileStoreException(STORE_THE_FILE_ERROR + object);
    }

    public static FileStoreException readFileException(String object) {
        return new FileStoreException(READ_THE_FILE + object);
    }

    public static FileStoreException errorFileException(String object) {
        return new FileStoreException(ERROR + object);
    }

}
