package de.clerkvest.api.exception;

public class NotEnoughPermissionsException extends RuntimeException {

    public NotEnoughPermissionsException(String message) {
        super(message);
    }
}