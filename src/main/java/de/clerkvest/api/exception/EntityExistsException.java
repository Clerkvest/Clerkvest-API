package de.clerkvest.api.exception;

public class EntityExistsException extends RuntimeException {

    public EntityExistsException(String message) {
        super(message);
    }
}